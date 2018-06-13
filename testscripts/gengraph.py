import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from time import sleep
import localdb, sys

def fnx():
    return np.random.randint(5, 50, 10)

def genimage():
    y = np.row_stack((fnx(), fnx(), fnx()))
    x = np.arange(10)

    y1, y2, y3 = fnx(), fnx(), fnx()

    fig, ax = plt.subplots()
    ax.stackplot(x, y)
    plt.show()

    fig, ax = plt.subplots()
    ax.stackplot(x, y1, y2, y3)

    plt.savefig('display.png', bbox_inches='tight')
    #plt.show()

def genimage2():
    dts, psv, qrc, qc, aa = localdb.get_activity_data()

    N = 30
    ind = np.arange(N)    # the x locations for the groups
    width = 0.35       # the width of the bars: can also be len(x) sequence

    p1 = plt.bar(ind, psv, width, color='r')
    p2 = plt.bar(ind, qrc, width, color='y', bottom=psv)
    combined = [x+y for x,y in zip(psv, qrc)]
    p3 = plt.bar(ind, qc, width, color='b', bottom=combined)
    combined = [x+y for x,y in zip(combined, qc)]
    p4 = plt.bar(ind, aa, width, color='g', bottom=combined)

    plt.ylabel('Events(minutes ago)')
    plt.title('Events Sample Agg')
    plt.xticks(ind + width/2., dts)
    plt.legend((p1[0], p2[0], p3[0], p4[0]), ('PresentationSlideViewed', 'QuestionResponseCreated', 'QuestionCreated', 'ActivityAnswered'))

    plt.savefig('display.png', bbox_inches='tight')
    plt.close()

def gen_image_active_users():
    dts, cnt = localdb.get_active_user_data()
    
    N = 100
    ind = np.arange(N)    # the x locations for the groups
    width = 0.55       # the width of the bars: can also be len(x) sequence

    p1 = plt.bar(ind, cnt, width, color='#7E1137')

    plt.ylabel('')
    plt.title('Active Users (Spark Frame)')
    #plt.xticks(ind + width/2., dts)

    plt.savefig('display.png', bbox_inches='tight')
    plt.close()

if __name__ == "__main__":
    gen_image_active_users()
    sys.exit(0)
    while True:
        genimage3()
        print("image gen")
        sleep(1)
