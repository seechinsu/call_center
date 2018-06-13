#!/bin/bash -e

rm -f ./spark-job/compare.1.txt
rm -f ./spark-job/compare.2.txt

cat spark-job/spark.log | grep Received | awk '{print $9" "$11}' | sort
echo "compared to :"
cat spark-job/generated_events.log | awk -F' ' '{print $2" "$4}' | sort

cat spark-job/spark.log | grep Received | awk '{print $9" "$11}' | sort > ./spark-job/compare.1.txt
cat spark-job/generated_events.log | awk -F' ' '{print $2" "$4}' | sort > ./spark-job/compare.2.txt

if [ ! -s ./spark-job/compare.1.txt ]
then
    echo "Empty results"
    exit 1
fi

if ! diff ./spark-job/compare.1.txt ./spark-job/compare.2.txt;
    then
    echo "FAIL ouptut counts did not match."
    exit 1
else
    echo "Pass"

fi
