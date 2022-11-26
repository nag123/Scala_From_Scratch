package com.codewithme.LowLevelAPI

import com.codewithme.AdvancedScalaConcepts.Common.Stock
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.DStream

import java.io.{File, FileWriter}
import java.sql.Date
import java.text.SimpleDateFormat
object DStreams {
  val spark = SparkSession.builder().appName("DStreams").master("local[2]").getOrCreate()
  //SparkStreamingContext - entry point to spark DStream API - sparkContext , Duration -> (Batch Interval) needs to be passed

  //>ncat -lk 5554
val ssc  = new StreamingContext(spark.sparkContext,Seconds(1))
/*
* Define input sources by creating DStreams
* Define transformations on DStreams
* call action on dstreams
* start ALL computations with ssc.start()
  * No more computations can be added
* Await termination, or stop the computation
  * You cannot restart a computation - SSC
* */

  def readFromSocket(): Unit ={
    val socketStream : DStream[String] = ssc.socketTextStream("localhost",5554)
    //Transformations -> They are lazy until a action like print is given
    val wordStream : DStream[String] = socketStream.flatMap(line => line.split(" ")) // array flattened to Dstream

  //  wordStream.print() -> Instead of printing we are going to write the data to a folder in the below path
    var pathofsocketdata = s"src/main/resources/data/words" //Manually test in the loc and deleted run again and you can see the data
    wordStream.saveAsTextFiles(pathofsocketdata)
    //Each folder is an RDD/Batch.and each file is a partition of RDD
  //to trigger computation
    ssc.start()
    ssc.awaitTermination() //To wait indefinitely
  }

  def createNewFile(): Unit ={
    //Here we are creating another thread to create a new file
    new Thread(() =>{
     Thread.sleep(5000)
      //Creating new files based on length
      val path = "src/main/resources/data/stocks"
      val dir = new File(path) //new file Directory
      val nFiles = dir.listFiles().length
      val newFile = new File(s"$path/newStocks$nFiles.csv")
newFile.createNewFile()

      val writer = new FileWriter(newFile)
      writer.write(
        """
          |AAPL,Jun 1 2009,142.43
          |AAPL,Jul 1 2009,163.39
          |AAPL,Aug 1 2009,168.21
          |AAPL,Sep 1 2009,185.35
          |AAPL,Oct 1 2009,188.5
          |AAPL,Nov 1 2009,199.91
          |""".stripMargin.trim)
writer.close()
    }).start
  }
/*Nothing works since textFileStream will monitor a directory for a new files*/
  def readFromFiles(): Unit ={
    createNewFile() //This is asyn since t operates on another thread
    val stocks = "src/main/resources/data/stocks"
    val txtStream : DStream[String] = ssc.textFileStream(stocks)

    //Transformation
    val dateFormat = new SimpleDateFormat("MMM d yyyy")
    val stockStream : DStream[Stock] = txtStream.map{
      line =>
        val token = line.split(",")
        val company = token(0)
        val date = new Date(dateFormat.parse(token(1)).getTime)
        val price = token(2).toDouble

        Stock(company,date,price)
    }
//action
    stockStream.print()

    //start the computation
    ssc.start()
    ssc.awaitTermination()
  }

  def main(args: Array[String]): Unit = {
//readFromSocket()
  //  readFromFiles()
    readFromSocket()  //Words split into space are written to the files from socket to directory
  }
}
