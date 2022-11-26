package com.codewithme.AdvancedScalaConcepts

import org.apache.spark.sql.types._

package object Common {

  val carSchema = StructType(
    Array(
      StructField("Name", StringType),
        StructField("Miles_per_Gallon", DoubleType),
        StructField("Cylinders", LongType),
        StructField("Displacement", DoubleType),
        StructField("Horsepower", LongType),
        StructField("Weight_in_lbs", LongType),
        StructField("Acceleration", DoubleType),
        StructField("Year", StringType),
        StructField("Origin", StringType)
    )
  )

  val stockSchema = StructType(
                                Array(
                                      StructField("company",StringType),
                                      StructField("date",DateType),
                                      StructField("value",DoubleType),
                                      )
                                )
}
