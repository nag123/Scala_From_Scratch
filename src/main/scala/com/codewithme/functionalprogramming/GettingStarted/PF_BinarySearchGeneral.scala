package com.codewithme.functionalprogramming.GettingStarted

object PF_BinarySearchGeneral {
 // ds = 7  9  11  13  15  17  19  21
  /*def BS(ds : Array[Double],key:Int):Int = {
    @annotation.tailrec
    def go(low:Int,mid:Int,high:Int):Int = {
      if(low > high) -mid -1
      else{
        val mid2 = (low + high) /2
        val d = ds(mid2)
        if(d == key) mid2
        else if(d>key) go(low,mid2,mid2-1) // Consider 11 is the key . 15 > 11 . It comes in the first part of the array
        else go(mid2+1,mid2,high)
      }
    }
    go(0,0,ds.length-1)
  }*/

  def BS[A](ds:Array[A],key:A,gt: (A,A) => Boolean):Int =
  {
    @annotation.tailrec
    def go(low : Int,mid : Int,high : Int):Int = {
      if (low > high) -mid - 1
      else {
        val mid2 = (low + high) / 2
        val d = ds(mid2)
        val greater = gt(d, key)
        if (!greater && !gt(key, d)) mid2
        else if (greater) go(low, mid2, mid2 - 1)
        else go(mid2 + 1, mid2, high)
      }
    }
      go(0,0,ds.length-1)
  }

  /** Exercise 2
   *
   * Check isSorted, which checks whether an Array[A] is sorted according to a given comparison function.
   */
  def isSorted[A](as: Array[A], gt: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def iter(i: Int): Boolean = {
      if (i >= as.length - 1) true
      else !gt(as(i), as(i + 1)) && iter(i + 1)
    }
    iter(0)
  }
  /*def binarySearchWithSpecialized[@specialized A](as: Array[A], key: A,
                                   gt: (A,A) => Boolean): Int = {


  }*/

    def main(args : Array[String]):Unit = {
      var arrayval = Array(7,9,11,13,15,17,19,21)
      var key = 11
      //print(BS[Int](arrayval,key,(a: Int, b: Int) => a > b))
      println(isSorted[Int](arrayval,(a : Int,b:Int) => a > b))
    }
}
