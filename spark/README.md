## Docker Spark Test Environment

This compose contains:

  - Spark 2.1.0
  - Kafka 10
  - Kafkacat
  - Zookeeper

## Simple Usage


To start environment

```
make up
```

To compile the spark logger

```
make package
```

Run logger

```
make runjar
```

Produce Events

```
make producer events=10 wait=5
```

To test parquet counts

```
make up package test
```


