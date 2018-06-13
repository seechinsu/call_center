FROM debian:jessie

MAINTAINER Getty Images "https://github.com/gettyimages"

RUN apt-get update \
 && apt-get install -y locales \
 && dpkg-reconfigure -f noninteractive locales \
 && locale-gen C.UTF-8 \
 && /usr/sbin/update-locale LANG=C.UTF-8 \
 && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
 && locale-gen \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

# Users with other locales should set this in their derivative image
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN apt-get update \
 && apt-get install -y curl unzip \
    python3 python3-setuptools \
 && ln -s /usr/bin/python3 /usr/bin/python \
 && easy_install3 pip py4j \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

# http://blog.stuart.axelbrooke.com/python-3-on-spark-return-of-the-pythonhashseed
ENV PYTHONHASHSEED 0
ENV PYTHONIOENCODING UTF-8
ENV PIP_DISABLE_PIP_VERSION_CHECK 1

# JAVA
ARG JAVA_MAJOR_VERSION=8
ARG JAVA_UPDATE_VERSION=112
ARG JAVA_BUILD_NUMBER=15
ENV JAVA_HOME /usr/jdk1.${JAVA_MAJOR_VERSION}.0_${JAVA_UPDATE_VERSION}

ENV PATH $PATH:$JAVA_HOME/bin
RUN curl -sL --retry 3 --insecure \
  --header "Cookie: oraclelicense=accept-securebackup-cookie;" \
  "http://download.oracle.com/otn-pub/java/jdk/${JAVA_MAJOR_VERSION}u${JAVA_UPDATE_VERSION}-b${JAVA_BUILD_NUMBER}/server-jre-${JAVA_MAJOR_VERSION}u${JAVA_UPDATE_VERSION}-linux-x64.tar.gz" \
  | gunzip \
  | tar x -C /usr/ \
  && ln -s $JAVA_HOME /usr/java \
  && rm -rf $JAVA_HOME/man

# HADOOP
ENV HADOOP_VERSION 2.7.3
ENV HADOOP_HOME /usr/hadoop-$HADOOP_VERSION
ENV HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
ENV PATH $PATH:$HADOOP_HOME/bin
RUN curl -sL --retry 3 \
  "http://archive.apache.org/dist/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz" \
  | gunzip \
  | tar -x -C /usr/ \
 && rm -rf $HADOOP_HOME/share/doc \
 && chown -R root:root $HADOOP_HOME

# SPARK
#ENV SPARK_VERSION 2.0.2
ENV SPARK_VERSION 2.1.0
ENV SPARK_PACKAGE spark-${SPARK_VERSION}-bin-without-hadoop
ENV SPARK_HOME /usr/spark-${SPARK_VERSION}
ENV SPARK_DIST_CLASSPATH="$HADOOP_HOME/etc/hadoop/*:$HADOOP_HOME/share/hadoop/common/lib/*:$HADOOP_HOME/share/hadoop/common/*:$HADOOP_HOME/share/hadoop/hdfs/*:$HADOOP_HOME/share/hadoop/hdfs/lib/*:$HADOOP_HOME/share/hadoop/hdfs/*:$HADOOP_HOME/share/hadoop/yarn/lib/*:$HADOOP_HOME/share/hadoop/yarn/*:$HADOOP_HOME/share/hadoop/mapreduce/lib/*:$HADOOP_HOME/share/hadoop/mapreduce/*:$HADOOP_HOME/share/hadoop/tools/lib/*"
ENV PATH $PATH:${SPARK_HOME}/bin
RUN curl -sL --retry 3 \
  "http://d3kbcqa49mib13.cloudfront.net/${SPARK_PACKAGE}.tgz" \
  | gunzip \
  | tar x -C /usr/ \
 && mv /usr/$SPARK_PACKAGE $SPARK_HOME \
 && chown -R root:root $SPARK_HOME

WORKDIR $SPARK_HOME

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/apache/spark/spark-core_2.11/2.1.0/spark-core_2.11-2.1.0.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/apache/spark/spark-streaming_2.11/2.1.0/spark-streaming_2.11-2.1.0.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/apache/spark/spark-streaming-kafka-0-10_2.11/2.1.0/spark-streaming-kafka-0-10_2.11-2.1.0.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/apache/spark/spark-sql_2.11/2.1.0/spark-sql_2.11-2.1.0.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/apache/kafka/kafka_2.11/0.10.0.1/kafka_2.11-0.10.0.1.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/apache/kafka/kafka-clients/0.10.0.1/kafka-clients-0.10.0.1.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/mysql/mysql-connector-java/6.0.5/mysql-connector-java-6.0.5.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/com/typesafe/play/play-json_2.11/2.4.0-M2/play-json_2.11-2.4.0-M2.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/com/typesafe/play/play-datacommons_2.11/2.4.0-M2/play-datacommons_2.11-2.4.0-M2.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/com/typesafe/play/play-functional_2.11/2.4.0-M2/play-functional_2.11-2.4.0-M2.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/com/typesafe/play/play-iteratees_2.11/2.4.0-M2/play-iteratees_2.11-2.4.0-M2.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/org/joda/joda-convert/1.8.1/joda-convert-1.8.1.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O http://central.maven.org/maven2/com/amazonaws/aws-java-sdk/1.11.86/aws-java-sdk-1.11.86.jar

RUN cd /usr/hadoop-2.7.3/share/hadoop/tools/lib/ \
 && curl -O https://s3.amazonaws.com/athena-downloads/drivers/AthenaJDBC41-1.0.0.jar

COPY ./start_command.sh ./start_command.sh

COPY ./log4j.properties /usr/spark-2.1.0/conf/log4j.properties

RUN chmod +x start_command.sh

#CMD ["bin/spark-class", "org.apache.spark.deploy.master.Master"]

CMD ["./start_command.sh"]
