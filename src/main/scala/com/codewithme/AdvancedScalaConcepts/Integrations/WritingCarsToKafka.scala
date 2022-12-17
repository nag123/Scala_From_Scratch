package com.codewithme.AdvancedScalaConcepts.Integrations

import com.codewithme.AdvancedScalaConcepts.Common.carSchema
import org.apache.spark.sql.functions.{col, struct, to_json}
import org.apache.spark.sql.SparkSession

//Kafka with Spark streaming
object WritingCarsToKafka {
  val spark = SparkSession.builder().appName("Writing data in local to a kafka topic").master("local[2]").getOrCreate()

  def writeCarsToKafka() = {
    val carsDF = spark.readStream
      .schema(carSchema)
      .json("src/main/resources/data/cars")


    val carsJsonKafkaDF = carsDF.select(
      col("Name").as("key"),
      to_json(struct(col("Name"), col("Horsepower"), col("Origin"))).cast("String").as("value")
    )

    /*
    * {"Name":"chevrolet monte carlo s","Horsepower":145,"Origin":"USA"}
    {"Name":"pontiac grand prix","Horsepower":230,"Origin":"USA"}
    {"Name":"fiat 128","Horsepower":49,"Origin":"Europe"}
    {"Name":"opel manta","Horsepower":75,"Origin":"Europe"}
    {"Name":"audi 100ls","Horsepower":91,"Origin":"Europe"}*/


    //Dont forget to delete the checkpoint location in local when you are going to run again
    carsJsonKafkaDF.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "rockthejvm")
      .option("checkpointLocation", "checkpoints")
      .start()
      .awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    writeCarsToKafka()
  }
}
  /* Up your docker in terminal
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
  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic rockthejvm
    * bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic rockthejvm
    */

