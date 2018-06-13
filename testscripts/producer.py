#!/usr/local/bin/python3.5
from kafka import KafkaProducer
import datetime, os, uuid, random, time, traceback, re, datetime
from optparse import OptionParser

def replaceUUIDs(line, new_institution_id, new_user_id):

    pos = line.index('institutionId')
    current_id = line[pos+16:pos+52]
    line = line.replace(current_id, str(new_institution_id))

    pos = line.index('userId')
    current_id = line[pos+9:pos+45]
    line = line.replace(current_id, str(new_user_id))

    return line

def runProducer(broker, iteration_count, wait_per_iteration, events_per_iteration, graph_refresh):    
    print("broker : " + broker)
    producer = KafkaProducer(bootstrap_servers=broker)

    inids = ['99d3931a-3acf-4ed9-b809-718d9e85df14','ffb14c4d-3a28-4e23-b89a-ea25b8edbf6b','d630a6e8-f2dc-494f-922f-4fea94ae45c1']

    # user ids from test env
    uids = ['27a5db01-8830-480c-801b-4969ac08d90d','2f29181c-0e6f-47cf-9142-29b80c099540',
        'a466be51-8b8b-4002-b8ad-84a7da602597','67fdbb9c-0d19-4020-9d82-af49bb54af6e',
        '8c529b88-cc14-438b-851c-a4ea67568c99']
        
    # event types off json files in local directory
    eventtypes = {}
    for filename in os.listdir('.'):
        if '.json' in filename and os.path.isfile(filename):
            line = [line.rstrip('\n') for line in open(filename)][0]
            eventtypes[filename[:-5]] = line

    # count events generated
    eventcounts = {}
    for event_name in eventtypes.keys():
        eventcounts[event_name] = 0

    eventpayload = {}
    for event_name in eventtypes.keys():
        eventpayload[event_name] = ''

    event_type_count = len(eventtypes.keys())
    user_count = len(uids)
    institution_count = len(inids)

    for i in range(iteration_count):
        print('Iteration Number',i)
        now_formatted = '{:%Y-%m-%dT%H:%M:%S.%f}'.format(datetime.datetime.now())[:-3]+'Z'
        for e in range(events_per_iteration):
            ri = random.randint(0,100000)

            event_index = list(eventtypes.keys())[int(ri % event_type_count)]
            user_index = ri % user_count
            institution_index = ri % institution_count

            json = replaceUUIDs( eventtypes[event_index], inids[institution_index], uids[user_index])
            json = re.sub(r"[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z", now_formatted, json)

            eventcounts[event_index] += 1
            eventpayload[event_index] += json + '\n'

        for event_name in eventtypes.keys():
            print('\t' + event_name + ' : ' + str(eventcounts[event_name]))
            payload = eventpayload[event_name][:-1]
            eventpayload[event_name] = ''
            producer.send(event_name, payload.encode('utf-8'))

        time.sleep(wait_per_iteration)

    f=open('./spark-job/generated_events.log', 'w+')
    for event_name in eventtypes.keys():
        print('Generated ' + event_name + ' : ' + str(eventcounts[event_name]))

        f.write('Generated ' + event_name + ' : ' + str(eventcounts[event_name]) + '\n')

if __name__ == "__main__":
    try:
        parser = OptionParser(usage="usage: %prog [options]", version="%tester 1.0")
        parser.add_option("-i", dest="iteration_count", type='int', default=10, help="iteration_count")
        parser.add_option("-w", action="store", dest="wait_per_iteration", type='int', default=2, help="wait_per_iteration(seconds)")
        parser.add_option("-e", action="store", dest="events_per_iteration", type='int', default=5, help="events_per_iteration")
        parser.add_option("-g", action="store", dest="graph_refresh", type='int', default=0, help="graph_refresh")
        parser.add_option("-b", action="store", dest="broker", type='string', default="kafka.local:9092", help="kafka broker")
        (options, args) = parser.parse_args()

        runProducer(options.broker, options.iteration_count, options.wait_per_iteration, options.events_per_iteration, options.graph_refresh)

    except Exception as e:
        traceback.print_exc()



