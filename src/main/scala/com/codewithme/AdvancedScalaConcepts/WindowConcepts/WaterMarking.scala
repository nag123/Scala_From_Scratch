package com.codewithme.AdvancedScalaConcepts.WindowConcepts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{StreamingQuery,Trigger}
import org.apache.spark.sql.types._
import scala.concurrent.duration._
import java.sql.Timestamp
import java.io.PrintStream
import java.net.ServerSocket

object WaterMarking {
  val spark = SparkSession.builder().appName("Watermarking concept").master("local[2]").getOrCreate()
//12345,blue
  import spark.implicits._

  def debugQuery(query : StreamingQuery)={
    new Thread(() => {
      (1 to 100).foreach {i =>
        Thread.sleep(1000)
        val queryEventTime =
          if(query.lastProgress == null) "[]" else query.lastProgress.eventTime.toString

      }
    }
    )
  }

  def  watermarks(): Unit ={
    val dataDF = spark.readStream.format("socket").option("host","localhost").option("port",5554).load().as[String].map{
      line =>
        val tokens = line.split(",")
        val timestamp = new Timestamp(tokens(0).toLong)
        val data = tokens(1)
        (timestamp,data)
    }.toDF("created","color")

    val watermarkedDF = dataDF.withWatermark("created","2 seconds")
      .groupBy(window(col("created"),"2 seconds")).count()
      .selectExpr("window.*","color","count")

    val query = watermarkedDF.writeStream.format("console").outputMode("append").trigger(Trigger.ProcessingTime(2.seconds)).start()

    debugQuery(query)
    query.awaitTermination()

  }
/*
* A 2 second watermark means
* - a window will only be considered until the watermark surpasses the window end
* - An element / a row / a record will be considered if AFTER the watermark
* */

  // sending data "manually" through socket to be as deterministic as possible
  object DataSender {
    val serverSocket = new ServerSocket(12345)
    val socket = serverSocket.accept() // blocking call
    val printer = new PrintStream(socket.getOutputStream)

    println("socket accepted")

    def example1() = {
      Thread.sleep(7000)
      printer.println("7000,blue")
      Thread.sleep(1000)
      printer.println("8000,green")
      Thread.sleep(4000)
      printer.println("14000,blue")
      Thread.sleep(1000)
      printer.println("9000,red") // discarded: older than the watermark
      Thread.sleep(3000)
      printer.println("15000,red")
      printer.println("8000,blue") // discarded: older than the watermark
      Thread.sleep(1000)
      printer.println("13000,green")
      Thread.sleep(500)
      printer.println("21000,green")
      Thread.sleep(3000)
      printer.println("4000,purple") // expect to be dropped - it's older than the watermark
      Thread.sleep(2000)
      printer.println("17000,green")
    }
  }

    def main(args: Array[String]): Unit = {

    }

  }
