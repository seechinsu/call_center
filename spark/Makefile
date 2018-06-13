
ymls = -f docker-compose.yml -f docker-compose.override.yml

.PHONY: up down clean consume produceone produceten producer

up:
	docker-compose $(ymls) up -d

down:
	docker-compose $(ymls) down

clean:
	docker-compose $(ymls) down --rmi local
	docker volume ls -qf dangling=true | xargs docker volume rm
	docker rm -f $$(docker ps -a -q)

runjar:
	docker-compose $(ymls) exec s3 mkdir -p /data/s3proxy/
	docker-compose $(ymls) exec spark spark-submit \
	  --properties-file ./spark-job/spark.properties \
	  --class "sparks3logger.logger" \
	  --master local[*] ./spark-job/sparks3logger_2.11-1.0.0.jar

producer:
	docker cp ./testscripts/producer.py $$(docker-compose ps | grep _test | awk '{print $$1}'):/usr/src/app/producer.py   
	docker-compose $(ymls) exec testscripts python producer.py -i 10 -w $(wait) -e $(events) -b kafka.local:9092

package:
	docker exec dockerspark_spark-s3-logger_1 sbt package

assembly:
	docker-compose $(ymls) exec spark-s3-logger sbt assembly

shell:
	docker-compose $(ymls) exec spark spark-shell

counts:
	docker-compose $(ymls) exec spark spark-shell -i ./spark-job/querys3proxycounts.scala | grep Received

test:
	@echo "clearing data in spark and s3"
	rm -rf ./spark-job/spark.log
	rm -rf ./spark-job/generated_events.log
	docker exec dockerspark_s3_1 sh -c "mkdir -p /data/s3proxy/"
	docker exec dockerspark_s3_1 sh -c "rm -rf /data/s3proxy/data/"
	docker-compose $(ymls) restart spark-test-env
	docker exec dockerspark_spark-test-env_1 sh -c "rm -rf /stage/"
	sleep 30
	docker exec -t dockerspark_testscripts_1 python producer.py -i 10 -w 20 -e 100 -b kafka.local:9092
	sleep 120
	docker exec -t dockerspark_spark_1 spark-shell -i ./spark-job/querys3proxycounts.scala | grep Received
	./spark-job/compare.sh
