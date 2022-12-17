package com.codewithme.AdvancedScalaConcepts.Integrations

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import com.codewithme.AdvancedScalaConcepts.Common.carSchema

object IntegratingKafka {

  val spark = SparkSession.builder()
    .appName("Integrating Kafka")
    .master("local[2]")
    .getOrCreate()
//Here we are going to subscribe on the topic rockthejvm and read the data as in kafka format
  def readFromKafka() = {
    // https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html
//From our localhost server we are going to subscribe to rockthejvm and whenever the data comes into the rockthejvm server , we will get
    //data from the offset example . 0 to 100 for today then again nxt day from 100 offset we will get the data

    //watevr i write in localhost : 9092 (console) will be feed into the topic rockthejvmnaxcopy topic
    val kafkaDataFrame : DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("subscribe","rockthejvm")
      .load()

    kafkaDataFrame
      .select(col("topic"), expr("cast(value as string) as actualValue"))
      .writeStream
      .format("console")  //in the output window
      .outputMode("append")
      .start()
      .awaitTermination()

  }

/*
*
* Up your docker in terminal
terminal 1
================
docker-compose-v1 up

terminal 2
=================
docker ps
docker exec -it rockthejvm-sparkstreaming-kafka bash
cd opt/kafka_2.12_-2.4.0
ls

 bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic rockthejvm
 * bin/kafka-console-producer.sh --broker-list localhost:9092 --topic rockthejvm
bin/kafka-console-producer.sh --topic rockthejvm

run the program in intellij
* */

  def main(args: Array[String]): Unit = {
    readFromKafka()
  }
}