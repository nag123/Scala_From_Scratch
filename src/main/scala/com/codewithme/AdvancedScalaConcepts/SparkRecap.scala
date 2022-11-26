package com.codewithme.AdvancedScalaConcepts

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


object SparkRecap {

  val spark = SparkSession.builder()
                          .appName("Lets Recap our Spark Understanding")
                          .master("local[2]")
                          .getOrCreate()


  import spark.implicits._
  var cars = spark.read.format("json").option("inferSchema","true").load("src/main/resources/data/cars/cars.json")

  //1. Selecting
  val usefulcardatas = cars.select(
    col("Name"),
    $"Year",
    //Transform Weight_in_lbs to Weight_in_kgs
    (col("Weight_in_lbs")/2.2).as("Weight_in_kgs"),
    //showing how to call using expr
    expr("Weight_in_lbs / 2.2").as("Weight_in_kgs_2")
  )

  val carsWeights = cars.selectExpr("Weight_in_lbs / 2.2")

  //2. filtering
  val europeanCars = cars.filter(col("Origin") =!= "USA")
  val europeanCarsusingwhere = cars.where(col("Origin") =!= "USA")

  //3. Aggregation
  val averageHP = cars.select(avg(col("HorsePower")).as("average_hp"))  //sum,mean,stddev,max,min

  //4. Grouping
  val countByOrigin = cars
    .groupBy(col("Origin")) //RelationalGroupSet
    .count()

  val bands = spark.read.format("json").option("inferSchema","true").load("src/main/resources/data/bands")
  val guitarPlayers = spark.read.format("json").option("inferSchema","true").load("src/main/resources/data/guitarPlayers")

  val guitaristBand = bands.join(guitarPlayers,bands.col("band") === guitarPlayers.col("id")) // Default =  Inner Join

  /** Inner- Only the matching rows
   * left
   * right
   * full
   * semi
   * anti
   */

    //Dataset - Typed distributed collections of objects
    case class GuitarPlayers(id : Long,name:String,guitars : Seq[Long] , band : Long)
  var guitarPlayersDS = guitarPlayers.as[GuitarPlayers] // needs spark impicits to convert
  guitarPlayersDS.map(_.name)

  //Spark Sql
  cars.createOrReplaceTempView("CarSS")
  val americanCars = spark.sql(
    """
      |select * from CarSS where Origin = "USA"
      |""".stripMargin)

  //low level API - RDDs only have functional API not spark sql API
  val sc = spark.sparkContext
  val numberAsRdd : RDD[Int]= sc.parallelize(1 to 100)

  //functional operator on RDDs
  val doubles = numberAsRdd.map(_ * 2)

  // RDD -> DF - It contains only one value of RDD[Int]
  val numberDF =  numberAsRdd.toDF("number") //lose type info but gain sql capability

  //RDD -> DS
  val numberDS = spark.createDataset(numberAsRdd)

  //DS -> RDD
  val guitarPlayersRDD = guitarPlayersDS.rdd

  //DF -> RDD
  val carsRDD = cars.rdd    //This is RDD[Row] means row of rdd value think it as excel or table row


  def main(args: Array[String]): Unit = {
    //read the car json file - Which is a dataframe now
    cars.printSchema()






  }

}
