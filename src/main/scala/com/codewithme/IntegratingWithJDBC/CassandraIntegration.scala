package com.codewithme.IntegratingWithJDBC
import com.codewithme.AdvancedScalaConcepts.Common
import com.codewithme.AdvancedScalaConcepts.Common.Car
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.{Dataset, ForeachWriter, SaveMode, SparkSession}

object CassandraIntegration {
  val spark = SparkSession.builder()
    .appName("IntegrationofCassandraInBatches")
    .master("local[2]")
    .config("spark.cassandra.connection.host", "127.0.0.1")
    .config("spark.cassandra.connection.port", "9042")
    .getOrCreate()

  def CassandraIntegrationInBatches(): Unit ={
    import spark.implicits._
    var CarsDS = spark.readStream.
                       schema(Common.carSchema).
                       json("src/main/resources/data/cars").
                       as[Car]

    CarsDS.writeStream.foreachBatch{(batch : Dataset[Car],_ : Long) =>
      {
        //Save this batch to Cassandra in a single table write
        batch.select($"Name",$"Horsepower")
          .write
          .cassandraFormat("cars","public")
          .mode(SaveMode.Append)
          .save()
      }

    } .start().awaitTermination()
  }

  class CarCassandraForEachWriter extends ForeachWriter[Car]{
    /* On every batch on every partition `partitionId`
      - on every "epoch" => chuck of data
        - calls the open method  ; if false , skip this chunk
        - for each entry in the chuck , cal the process method
        - calls the close method either at the end of chunk or with the error if it is thrown
      */

    val keyspace = "public"
    val table = "cars"
    val connector = CassandraConnector(spark.sparkContext.getConf)

    override def open(partitionId: Long, epochId: Long): Boolean = {
       println("The connection is open now")
      true
    }

    override def process(car: Car): Unit = {
      connector.withSessionDo{session =>
        session.execute(
          s"""
             |insert into $keyspace.$table("Name","Horsepower")
             |values('${car.Name}',${car.Horsepower.orNull})
             |""".stripMargin)
      }
    }
    //car.name is a string so single quotes
    //car.Horsepower is a int so no quotes
    override def close(errorOrNull: Throwable): Unit = println("The connection is closed now")
  }
  //Another Technique
  def WriteStreamToCassandra() = {
    import spark.implicits._
    var CarsDS = spark.readStream.
      schema(Common.carSchema).
      json("src/main/resources/data/cars").
      as[Car]

    CarsDS.writeStream.foreach(new CarCassandraForEachWriter).start().awaitTermination()
  }

  def main(args: Array[String]): Unit = {
  //CassandraIntegrationInBatches()
    WriteStreamToCassandra
  }

  /*
  * 1. Docker compose up
  * 2.
  * 3.
  * cqlsh> create keyspace public with replication = {'class' : 'SimpleStrategy','replication_factor':1};
cqlsh> create table public.cars("Name" text primary key,"Horsepower" int);
cqlsh> select * from public.cars;
* 4. Now run the program
* cqlsh> select * from public.cars; <<u ll see all the values >>
*cqlsh> truncate public.cars;
cqlsh> select * from public.cars;
  * */
}
