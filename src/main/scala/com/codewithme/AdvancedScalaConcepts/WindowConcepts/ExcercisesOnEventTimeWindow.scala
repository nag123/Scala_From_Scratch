package com.codewithme.AdvancedScalaConcepts.WindowConcepts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
object ExcercisesOnEventTimeWindow {
var spark = SparkSession.builder().appName("EventTimeWindow").master("local[2]").getOrCreate()

  val onlinePurchaseSchema  = StructType(Array(
    StructField("id",StringType),
    StructField("time",TimestampType),
    StructField("item",StringType),
    StructField("quantity",IntegerType)
  ))

  def readPurchasesFromFile() = {
    spark.readStream.schema(onlinePurchaseSchema).json("src/main/resources/data/purchases")
  }
  /* Excercise
  1 .Show the best selling product everyday +quantity of the product
   */
  def bestSellingProduct() = {
    val purchaseDF = readPurchasesFromFile()

    //If there is no slide duration based on time then it is a tumbling window
    val BestSellingItem = purchaseDF.groupBy(col("item"),window(col("time"),"1 day").as("day"))
      .agg(sum("quantity").as("totalQuantity"))
      .select(
        col("day").getField("start").as("start"),
        col("day").getField("end").as("end"),
        col("item"),
        col("TotalQuantity")
      ).orderBy(col("day"),col("TotalQuantity").desc)


    BestSellingItem.writeStream.format("console").outputMode("complete").start().awaitTermination()
  }
  /* EXERCISE  :
  *   2. Best selling product  of every 24 hours , updated every hour
  */
  //Sliding window since updated every hour
  def bestSellingProductEvery24Hours() = {
    val purchaseDF = readPurchasesFromFile()
    val bestSellingProductevery24hours = purchaseDF.groupBy(col("item"), window(col("time"), "1 day", "1 hour").as("time"))
      .agg(sum("quantity").as("totalQuantity"))
      .select(
        col("time").getField("start").as("start"),
        col("time").getField("end").as("end"),
        col("item"),
        col("totalQuantity")
      ).orderBy(col("time"),col("totalQuantity").desc)

    bestSellingProductevery24hours.writeStream.format("console").outputMode("complete").start().awaitTermination()
  }
  def main(args: Array[String]): Unit = {
    spark.conf.set("park.sql.streaming.forceDeleteTempCheckpointLocation",true)
   // bestSellingProduct()
    bestSellingProductEvery24Hours()
  }

}
