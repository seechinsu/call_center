#!/usr/local/bin/python3.5
import MySQLdb

conn = MySQLdb.connect(host='mysqldb.local', user='root', passwd='root')
conn.autocommit(True)

try:
    conn.cursor().execute('use spark_datastore');
except:

     with open('initdb.sql', 'r') as sqlfile:
        conn.cursor().execute(sqlfile.read())

def show_data():
    sql = """select * from institution"""
    cur = conn.cursor()
    cur.execute(sql)
    print(cur.fetchall())

def get_institution_ids():
    sql = """select id from institution limit 10"""

    ids = []

    cur = conn.cursor()
    cur.execute(sql)

    for row in cur.fetchall():
        ids.append(row[0])

    return ids

def get_user_ids():
    sql = """select id from user limit 100"""

    ids = []

    cur = conn.cursor()
    cur.execute(sql)

    for row in cur.fetchall():
        ids.append(row[0])

    return ids

def get_active_user_data():
    sql =   """
            select sub.val, coalesce(au.active_count,0)
            from
            (
            SELECT cast(val as unsigned) as val, from_unixtime(@curRank) as dt, @curRank as block, @curRank := @curRank - 10 AS adj from series u,
            (SELECT @curRank := (UNIX_TIMESTAMP(now()) - UNIX_TIMESTAMP(now()) % 10)) r
            order by val asc
            limit 100
            ) sub
               left outer join 
            (
            select active_count, from_unixtime(epoch) as dt, cast(@auRank := @auRank + 1 as unsigned) AS val
            from active_users,
            (SELECT @auRank := 0) r
            ORDER BY epoch desc limit 100
            ) as au
              on sub.val = au.val
            """
    dts = []
    cnt = []

    cur = conn.cursor()
    cur.execute(sql)

    for row in cur.fetchall():
        dts.append(str(row[0]))
        cnt.append(int(row[1]))

    cur.close()

    return dts, cnt

def get_activity_data():
    sql = """
select sub.dt, sub.val - 1
    , coalesce(a.PresentationSlideViewed,0), coalesce(a.QuestionResponseCreated,0)
    , coalesce(a.QuestionCreated,0), coalesce(a.ActivityAnswered,0)
from
(
SELECT val, from_unixtime(@curRank) as dt, @curRank as block, @curRank := @curRank - 10 AS adj from series u,
(SELECT @curRank := (UNIX_TIMESTAMP(now()) - UNIX_TIMESTAMP(now()) % 10)) r
order by val asc
limit 30
) sub
    left outer join
    (
    select epoch - epoch % 10 as block
        , sum(case when eventType = 'PresentationSlideViewed' then 1 else 0 end) as PresentationSlideViewed
        , sum(case when eventType = 'QuestionResponseCreated' then 1 else 0 end) as QuestionResponseCreated
        , sum(case when eventType = 'QuestionCreated' then 1 else 0 end) as QuestionCreated
        , sum(case when eventType = 'ActivityAnswered' then 1 else 0 end) as ActivityAnswered
        , count(1) as cnt from activity
    group by epoch - epoch % 10
    order by epoch - epoch % 10 desc
    limit 30
    ) a
        on sub.block = a.block
order by val asc
    """

    dts = []
    psv = []
    qrc = []
    qc  = []
    aa  = []

    cur = conn.cursor()
    cur.execute(sql)

    for row in cur.fetchall():
        dts.append(int(row[1]))
        psv.append(int(row[2]))
        qrc.append(int(row[3]))
        qc.append(int(row[4]))
        aa.append(int(row[5]))

    cur.close()

    return dts, psv, qrc, qc, aa

if __name__ == "__main__":
    print(get_institution_ids())
    print(get_user_ids())






