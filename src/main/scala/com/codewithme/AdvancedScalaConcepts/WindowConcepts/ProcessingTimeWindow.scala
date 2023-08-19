package com.codewithme.AdvancedScalaConcepts.WindowConcepts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
/*Processing time window will be current timestamp we dont have a particular timestamp other than current timestamp to use*/
object ProcessingTimeWindow {
  var spark = SparkSession.builder().appName("Processing Time Window").master("local[2]").getOrCreate()

  def aggregateByProcessingTime() = {
    val countNoofCharactersinaLine = spark.readStream.format("socket")
      .option("host","localhost")
      .option("port",5554)
      .load()
      .select(
        col("value"),current_timestamp().as("processingTime")).
      groupBy(window(col("processingTime"),"10 seconds").as("window")).
      agg(sum(length(col("value"))).as("CharCount")).
      select(
        col("window").getField("start").as("start"),
        col("window").getField("end").as("end"),
        col("charCount")
      )

    countNoofCharactersinaLine.writeStream.format("console").outputMode("complete").start().awaitTermination()

  }
  def main(args: Array[String]): Unit = {
    aggregateByProcessingTime()
  }
}
/*
1. ncat -lk 5554
2. Run the application in intellij
-------------------------------------------
Batch: 0
-------------------------------------------
+-----+---+---------+
|start|end|charCount|
+-----+---+---------+
+-----+---+---------+

3. Type the below in the console
* We are going to learn about event based window and processing based window
where event based window needs a time stamp however
processing window doesnt need it
because it uses current timestamp
but in case of event based window we need a timestamp column
and we learn about complete mode and append mode
*


-------------------------------------------
Batch: 1
-------------------------------------------
+-------------------+-------------------+---------+
|              start|                end|charCount|
+-------------------+-------------------+---------+
|2023-01-07 19:44:10|2023-01-07 19:44:20|      305|
+-------------------+-------------------+---------+

I am typing some more lines to see the batch result
here the batch size is 10 seconds

-------------------------------------------
Batch: 2
-------------------------------------------
+-------------------+-------------------+---------+
|              start|                end|charCount|
+-------------------+-------------------+---------+
|2023-01-07 19:44:10|2023-01-07 19:44:20|      305|
|2023-01-07 19:45:50|2023-01-07 19:46:00|       84|
+-------------------+-------------------+---------+
lets have minimal this time
-------------------------------------------
Batch: 3
-------------------------------------------
+-------------------+-------------------+---------+
|              start|                end|charCount|
+-------------------+-------------------+---------+
|2023-01-07 19:44:10|2023-01-07 19:44:20|      305|
|2023-01-07 19:47:20|2023-01-07 19:47:30|       27|
|2023-01-07 19:45:50|2023-01-07 19:46:00|       84|
+-------------------+-------------------+---------+

mini

-------------------------------------------
Batch: 4
-------------------------------------------
+-------------------+-------------------+---------+
|              start|                end|charCount|
+-------------------+-------------------+---------+
|2023-01-07 19:44:10|2023-01-07 19:44:20|      305|
|2023-01-07 19:47:20|2023-01-07 19:47:30|       27|
|2023-01-07 19:45:50|2023-01-07 19:46:00|       84|
|2023-01-07 19:50:10|2023-01-07 19:50:20|        4|
+-------------------+-------------------+---------+


*/