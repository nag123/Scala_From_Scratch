package com.codewithme.AdvancedScalaConcepts.SparkStreaming

import org.apache.spark.sql.{DataFrame, Dataset, Encoders, SparkSession}
import org.apache.spark.sql.functions._
import com.codewithme.AdvancedScalaConcepts.Common.{Car, carSchema}

object StreamingDatasets {

  val spark = SparkSession.builder().appName("Streaming Dataset").master("local[2]").getOrCreate()
  import spark.implicits._

  //To specify the encoder explicitly

  def readCars() =
    {
      val carEncoder = Encoders.product[Car]

      spark.readStream.format("socket").option("host","localhost").option("port",5554).load()
        .select(from_json(col("value"),carSchema).as("car")).selectExpr("car.*") //now df with multiple column
        //.as[Car]  //Here spark figure it out which implicit// Encoder passed explicitly as below
        .as[Car](carEncoder)//df to ds transformation
    }


def showCars() = {
var carsDS  = readCars()
var carsDF  = carsDS.select($"Name")
  //Collections maintains a type info
  var carsDFAlt = carsDS.map(_.Name)
  carsDFAlt.writeStream.format("console").outputMode("append").start.awaitTermination()
}
  /*
  * EXCERCISE :
  * COUNT THE NO OF POWERFUL CARS IN DS (HS >140)
  * AVERAGE HP FOR ENTIRE DATASET ( USE COMPLETE OUTPUT MODE)
  * COUNT THE CARS BY ORIGIN
  * */

  def ex1()= {
    var carsDS = readCars()
    var powerfulcars = carsDS.filter(col("Horsepower")>140)
    powerfulcars.writeStream.format("console").outputMode("append").start.awaitTermination()
  }

  def ex2()= {
    var carsDS = readCars()
    var avgHPCars = carsDS.select(avg(col("Horsepower")))
    avgHPCars.writeStream.format("console").outputMode("append").start.awaitTermination()
  }

  def ex3()= {
    var carsDS = readCars()
    var origincars = carsDS.groupBy(col("Origin")).count()
    var origindsopn = carsDS.groupByKey(cars => cars.Origin).count()
    origincars.writeStream.format("console").outputMode("complete").start.awaitTermination()
  }
   def main(args: Array[String]): Unit =
     {
       ex3()
     }
}
