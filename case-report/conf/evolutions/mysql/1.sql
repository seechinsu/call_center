CREATE TABLE ints (
  i TINYINT
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO ints VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9);

CREATE TABLE day (
  dt DATE NOT NULL PRIMARY KEY
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO day (dt)
  SELECT DATE('2000-01-01') + INTERVAL a.i * 10000 + b.i * 1000 + c.i * 100 + d.i * 10 + e.i DAY
  FROM ints a
    JOIN ints b
    JOIN ints c
    JOIN ints d
    JOIN ints e
  WHERE DATE('2000-01-01') + INTERVAL a.i * 10000 + b.i * 1000 + c.i * 100 + d.i * 10 + e.i DAY < '2050-01-01'
ORDER BY 1;

CREATE TABLE hour (
  hr INT NOT NULL PRIMARY KEY
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO hour VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12),
  (13), (14), (15), (16), (17), (18), (19), (20), (21), (22), (23);
CREATE TABLE intra_hour (
  mn   INT         NOT NULL,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (mn, name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO intra_hour VALUES (0, 'quarterHourly'), (15, 'quarterHourly'), (30, 'quarterHourly'), (45, 'quarterHourly'),
  (0, 'fiveMinutes'), (5, 'fiveMinutes'), (10, 'fiveMinutes'), (15, 'fiveMinutes'), (20, 'fiveMinutes'),
  (25, 'fiveMinutes'),
  (30, 'fiveMinutes'), (35, 'fiveMinutes'), (40, 'fiveMinutes'), (45, 'fiveMinutes'), (50, 'fiveMinutes'),
  (55, 'fiveMinutes');

CREATE TABLE report (
  id        SERIAL PRIMARY KEY,
  name      VARCHAR(50)                                       NOT NULL,
  frame     VARCHAR(20)                                       NOT NULL,
  bootstrap DATETIME                                          NOT NULL,
  lag       INT                                               NOT NULL,
  part      VARCHAR(20)                                       NOT NULL,
  query     VARCHAR(10000)                                    NOT NULL,
  status    ENUM ('ACTIVE', 'DISABLED', 'FAILING', 'RETIRED') NOT NULL DEFAULT 'ACTIVE',
  UNIQUE KEY unique_name (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE job (
  id           SERIAL,
  report_id    BIGINT UNSIGNED,
  status       ENUM ('SUBMITTED', 'QUEUED', 'RUNNING', 'COMPLETE', 'FAILED'),
  attempts     INT NOT NULL DEFAULT 0,
  aggr_start   DATETIME,
  aggr_end     DATETIME,
  queued_at    DATETIME,
  executed_at  DATETIME,
  completed_at DATETIME,
  records      INT,
  fail         VARCHAR(10000),
  FOREIGN KEY ref_report_id(report_id) REFERENCES report (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE dimension (
  id        SERIAL PRIMARY KEY,
  report_id BIGINT UNSIGNED,
  name      VARCHAR(100),
  type      VARCHAR(100),
  FOREIGN KEY ref_report_id(report_id) REFERENCES report (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE metric (
  id        SERIAL PRIMARY KEY,
  report_id BIGINT UNSIGNED,
  name      VARCHAR(100),
  type      VARCHAR(100),
  FOREIGN KEY ref_report_id(report_id) REFERENCES report (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE time_reference (
  reference DATETIME
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE FUNCTION frame_reference()
  returns DATETIME not deterministic
  BEGIN
    DECLARE dt datetime;;
select ifnull(max(reference),now())
INTO   dt
FROM   time_reference;;
return dt;;
end;

CREATE VIEW all_frames AS
  SELECT
    report.id,
    report.name,
    cast(dt AS DATETIME)                  AS start,
    cast(dt AS DATETIME) + INTERVAL 1 DAY AS end,
    report.frame,
    lag,
    report.query
  FROM report, day d
  WHERE dt >= bootstrap AND report.frame = 'daily'
        AND cast(dt AS DATETIME) + INTERVAL 1 DAY <= frame_reference() - INTERVAL 2 MINUTE
  UNION ALL
  SELECT
    report.id,
    report.name,
    cast(dt AS DATETIME) + INTERVAL h.hr HOUR AS start,
    cast(dt AS DATETIME) + INTERVAL h.hr + 1     HOUR AS end,
    report.frame,
    lag,
    report.query
  FROM report, day d, hour h
  WHERE dt >= bootstrap AND report.frame = 'hourly'
        AND cast(dt AS DATETIME) + INTERVAL h.hr + 1 HOUR <= frame_reference() - INTERVAL 2 MINUTE;

CREATE TABLE composite (
  name  VARCHAR(100) PRIMARY KEY,
  query TEXT NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE requires (
  report_id  BIGINT UNSIGNED NOT NULL,
  depends_on BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY requires_referrer_report_id(report_id) REFERENCES report (id)
    ON DELETE CASCADE,
  FOREIGN KEY requires_referent_report_id(depends_on) REFERENCES report (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
