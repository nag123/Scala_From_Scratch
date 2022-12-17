package com.codewithme.AdvancedScalaConcepts.Integrations

import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ReadingKafkaDStreams {

  var spark = SparkSession.builder().appName("Reading from Kafka Dstreaming").master("local[2]").getOrCreate()
  var ssc = new StreamingContext(spark.sparkContext,Seconds(1))

  val kafkaParams: Map[String, Object] = Map(
    "bootstrap.servers" -> "localhost:9092",
    "key.serializer" -> classOf[StringSerializer], // send data to kafka
    "value.serializer" -> classOf[StringSerializer],
    "key.deserializer" -> classOf[StringDeserializer], // receiving data from kafka
    "value.deserializer" -> classOf[StringDeserializer],
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> false.asInstanceOf[Object]
  )

  val kafkaTopic = "rockthejvm"

  def readFromKafka() = {
    val topics = Array(kafkaTopic) // we are reading from the topic , we can give 1 or more topic to consume from
    val kafkaDstream = KafkaUtils.createDirectStream(
      ssc,LocationStrategies.PreferConsistent, //Distributes the partition evenly across the spark cluster
      ConsumerStrategies.Subscribe[String,String](topics,kafkaParams + ("group.id" -> "group1"))
      /*
       Alternative
       - SubscribePattern allows subscribing to topics matching a pattern
       - Assign - advanced; allows specifying offsets and partitions per topic
      */
    )

    val processedStream = kafkaDstream.map(records => (records.key(),records.value()))
    processedStream.print()

    ssc.start()
    ssc.awaitTermination()
  }
  def main(args: Array[String]): Unit = {
readFromKafka()
  }
}
