package com.codewithme.AdvancedScalaConcepts.SparkStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, length}
import com.codewithme.AdvancedScalaConcepts.Common._
import org.apache.spark.sql.streaming.Trigger
import scala.concurrent.duration._

object StreamingDataframes {

  val spark = SparkSession.builder().appName("StreamingDF").master("local[2]").getOrCreate()

  def readFromSocket() = {
    var lines = spark.readStream.format("socket").option("host","localhost").option("port",9999).load()
    var shortLines = lines.filter(length(col("value") <= 5))
    val query = shortLines.writeStream.format("console").outputMode("append").start()

    query.awaitTermination()
  }

  def readFromFiles() = {
    val stocksDF = spark.readStream.format("csv").option("header","false").option("dateFormat","MMM d yyyy").schema(stockSchema).load("src/main/resources/data/stocks")
    val query = stocksDF.writeStream.format("console").outputMode("append").start().awaitTermination()

  }

  def DemoTrigger(): Unit ={
    var lines = spark.readStream.format("socket").option("host","localhost").option("port",9999).load()
    val query = lines.writeStream.format("console").outputMode("append")
      .trigger(
        //Trigger.ProcessingTime(2.seconds) // For every 2 seconds this dataframe is checked for data if the data is too long it comes in 2 or more batches
        // Trigger.Once() // only once it reads the data -> single batch and terminate
        Trigger.Continuous(2.seconds)   // Every 2 seconds creates batches with data you have
      )
      .start().awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    //readFromSocket()
    readFromFiles()
  }
}
