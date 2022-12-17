package com.codewithme.IntegratingWithJDBC

import org.apache.spark.sql.{Dataset, SparkSession}
import com.codewithme.AdvancedScalaConcepts.Common._

object JDBCIntegrationKafka {

  val spark = SparkSession.builder().appName("JDBCIntegration").master("local[2]").getOrCreate()

  val driver = "org.postgresql.Driver"
   val url = "jdbc:postgresql://localhost:5432/rtjvm"
  val user = "docker"
  val password = "docker"

  import spark.implicits._
  def WriteStreamToPostGres()={
    val carDF = spark.readStream.schema(carSchema).json("src/main/resources/data/cars")
    val carsDS = carDF.as[Car]

    //we cannot stream and write to a jdbc process so we are going to write it batch by batch

    carsDS.writeStream.foreachBatch{(batch:Dataset[Car],_:Long) =>
      //each executors controls the batch
      batch.write.format("jdbc")
        .option("driver",driver)
        .option("url",url)
        .option("user",user)
        .option("password",password)
        .option("dbtable","public.cars")
        .save()
    }
      .start()
      .awaitTermination()

  }

  def main(args: Array[String]): Unit = {
    WriteStreamToPostGres
  }
}


/*
*STEPS TO RUN THE CODE
*
* in the source directory cmd path :
*
* 1. docker-compose-v1 up
* 2. another cmd -> from the project -> >docker exec -it rockthejvm-sparkstreaming-postgres psql -U docker
* 3. /l+ to check if rtjvm exist . If not  create database rtjvm;
* 4. Ctrl+D and docker exec -it rockthejvm-sparkstreaming-postgres psql -U docker -d rtjvm
* 5. Run the class file which loads the data from cars.json of our local to the docker.
* 6. In the cmd prompt : select * from public.cars;
* 7. Stop the job
*
* Error I faced :
*  batch.write
          .format("jdbc")
          .option("driver", driver)
          .option("url", url)
          .option("user", user)
          .option("password", password)
          .option("dbtable", "public.cars") <<Previously i specified options as "database" which is wrong>>
          .save()
* */