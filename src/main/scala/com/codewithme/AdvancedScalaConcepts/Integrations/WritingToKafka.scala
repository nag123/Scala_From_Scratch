package com.codewithme.AdvancedScalaConcepts.Integrations

import com.codewithme.AdvancedScalaConcepts.Common.carSchema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, struct, to_json}

object WritingToKafka {

  //We are going to read from car data and write it to our topic
  val spark = SparkSession.builder()
    .appName("Integrating Kafka - Writing to Kafka Topic and seeing in console")
    .master("local[2]")
    .getOrCreate()
  //Here we are going to read from our localserver and write to the topic rockthejvm
  def writeToKafka() = {

    val carsDF = spark.readStream
      .schema(carSchema)
      .json("src/main/resources/data/cars")

    val carsKafkaDF = carsDF.selectExpr("upper(Name) as key", "Name as value")

    carsKafkaDF.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "rockthejvm")
      .option("checkpointLocation", "checkpoints") // without checkpoints the writing to Kafka will fail
      .start()
      .awaitTermination()
  }

  /**
   * Exercise: write the whole cars data structures to Kafka as JSON.
   * Use struct columns an the to_json function.
   */

  def main(args: Array[String]): Unit = {
    writeToKafka()
  }
}
  /*
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
  * bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic rockthejvm
*/