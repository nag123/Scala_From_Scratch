package com.codewithme.LowLevelAPI

import com.codewithme.AdvancedScalaConcepts.Common.Person
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.sql.Date
import java.time.{LocalDate, Period}

object DStreamTransformation {

  val spark = SparkSession.builder().appName("DStreamTransformation").master("local[2]").getOrCreate()
val ssc = new StreamingContext(spark.sparkContext,Seconds(1))

  def readPeople() = ssc.socketTextStream("localhost",5554).map{
    line =>
      val tokens = line.split(":")
      Person(
        tokens(0).toInt,
        tokens(1),
        tokens(2),
        tokens(3),
        tokens(4),
        Date.valueOf(tokens(5)),
        tokens(6),
        tokens(7).toInt
      )
  }

  //Map,flatMap,filter
def peopleAges = readPeople().map{
  Person =>
    val age = Period.between(Person.birthDate.toLocalDate,LocalDate.now).getYears
    (s"${Person.firstName} ${Person.lastName}",age) //Tuple of (firstname lastname,age)
}

  def peopleSmallNames = readPeople().flatMap{
    Person =>
      List(Person.firstName , Person.middleName)
  }

  //Filter

  //Count

  //reduceByKey


  def main(args: Array[String]): Unit = {
//1 . val streams = readPeople()
    //Open terminal ->C:\Users\HOME\IdeaProjects\Scala_From_Scratch\src\main\resources\data\people-1m> cat people-1m.txt | ncat -lk 5554
    val streams = peopleSmallNames
    streams.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
