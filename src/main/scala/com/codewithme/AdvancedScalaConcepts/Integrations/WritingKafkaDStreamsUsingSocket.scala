package com.codewithme.AdvancedScalaConcepts.Integrations

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.util

object WritingKafkaDStreamsUsingSocket {
val spark = SparkSession.builder().appName("Reading from socket and writing to kafka topic via Dstreams").master("local[2]").getOrCreate()
  val ssc = new StreamingContext(spark.sparkContext,Seconds(1))

  val kafkaParams : Map[String,Object] = Map(
    "bootstrap.server" -> "localhost:9092",
    "key.serializer" -> classOf[StringSerializer],
    "value.serializer" -> classOf[StringSerializer],
    "key.deserializer" -> classOf[StringDeserializer], // receiving data from kafka
    "value.deserializer" -> classOf[StringDeserializer],
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> false.asInstanceOf[Object]
  )

  var kafkaTopic = "rockthejvm"

  def WriteToKafka(): Unit = {
    val input = ssc.socketTextStream("localhost",5554)
    // we are getting the input from the socket and going to write to kafka topic

    //Transformation : We are going to convert the data to uppercase
    val processedData = input.map(x => x.toUpperCase())

    //Now we are going to process the Dstream[String] for each partition
    processedData.foreachRDD{
      rdd =>
        rdd.foreachPartition{
          partition =>

              // inside this lambda, the code is run by a single executor
              val kafkaHashMap = new util.HashMap[String,Object]()
              kafkaParams.foreach{
                pair => kafkaHashMap.put(pair._1,pair._2)
              }
              // producer can insert records into the Kafka topics available on this executor
val kproducer = new KafkaProducer[String,String](kafkaHashMap)

              partition.foreach{
                value =>
                  val message = new ProducerRecord[String,String](kafkaTopic,null,value)
                  // feed the message into the Kafka topic
                  kproducer.send(message)
              }
              kproducer.close()
            }
        }
        ssc.start()
        ssc.awaitTermination()
      }

  def main(args: Array[String]): Unit = {
    WriteToKafka()
  }

}
