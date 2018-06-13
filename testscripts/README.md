# Test Events Producer

docker-compose exec testscripts python producer.py -i 1 -w 1 -e 10 -b kafka.local:9092

-i iteration count

-w wait count between iterations

-b broker, defaults to kafka.local:9092

-e number of events per iteration
