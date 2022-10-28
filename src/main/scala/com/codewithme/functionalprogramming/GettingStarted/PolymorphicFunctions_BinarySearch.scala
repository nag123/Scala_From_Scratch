package com.codewithme.functionalprogramming.GettingStarted

object PolymorphicFunctions_BinarySearch {

  def binarySearch(ds: Array[Double], key: Double): Int = {
    @annotation.tailrec
    def go(low: Int, mid: Int, high: Int): Int = {
      if (low > high) -mid - 1
      else {
        val mid2 = (low + high) / 2
        val d = ds(mid2)
        if (d == key) mid2
        else if (d > key) go(low, mid2, mid2-1)
        else go(mid2 + 1, mid2, high)
      }
    }
    go(0, 0, ds.length - 1)
  }

  def main(args : Array[String]) : Unit = {
    var dataset = Array(0.0)
    var key = 1.1
    print("The result is :::: "+binarySearch(dataset,key))
  }
}
