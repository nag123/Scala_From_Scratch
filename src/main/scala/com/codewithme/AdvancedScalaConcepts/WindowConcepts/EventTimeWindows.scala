package com.codewithme.AdvancedScalaConcepts.WindowConcepts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object EventTimeWindows {

  val spark = SparkSession.builder().appName("Event Time Windows").master("local[2]").getOrCreate()

  val onlinePurchaseSchema  = StructType(Array(
    StructField("id",StringType),
    StructField("time",TimestampType),
    StructField("item",StringType),
    StructField("quantity",IntegerType)
  ))

def readPurchasesFromSocket() = {
  spark.readStream.format("socket")
    .option("host", "localhost")
    .option("port", 5554).load()
    .select(from_json(col("value"), onlinePurchaseSchema).as("purchase"))
    .selectExpr("purchase.*")
}
  def readPurchasesFromFile() = spark.readStream.schema(onlinePurchaseSchema).json("src/main/resources/data/purchases")

def aggregateBySlidingWindow() = {

  val purchasesDF = readPurchasesFromSocket()
  //Example for tumbling window
  val WindowByDay = purchasesDF.groupBy(window(col("time"),"1 day","1 hour").as("time"),col("item"))
    .agg(sum("quantity").as("totalQuantity"))
    .select(
      col("item"),
      col("time").getField("start").as("start"),
      col("time").getField("end").as("end"),
      col("TotalQuantity")
    )

  WindowByDay.printSchema()

  WindowByDay.writeStream.format("console") .outputMode("complete")
    .start()
    .awaitTermination()

}
  def main(args: Array[String]): Unit = {
    spark.conf.set("park.sql.streaming.forceDeleteTempCheckpointLocation",true)
    aggregateBySlidingWindow()
  }
}

/*
* HOW TO RUN THE APPLICATION :
*ncat -lk 5554
* run this application
*
* Event time is the time event has actually occurred. Event time has to be derived from the field in event, example : timestamp field.
* Processing time is the time when the event is processed.
*
* */
