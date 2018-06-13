#!/bin/bash
echo ${AUTO_START_JAR}
if [ ! -z "${AUTO_START_JAR}" ]; then
    if [ -e /usr/spark-2.1.0/spark-job/${AUTO_START_JAR} ]; then
        cd /usr/spark-2.1.0/spark-job && /usr/spark-2.1.0/bin/spark-submit --properties-file spark.properties --class "sparks3logger.logger" --master local[*] ${AUTO_START_JAR}
    else
        echo "if else"
        exit 1
    fi
else
    echo "else"
    /usr/spark-2.1.0/bin/spark-class org.apache.spark.deploy.master.Master
fi;
