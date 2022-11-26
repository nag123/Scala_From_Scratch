package com.codewithme.InterviewPrep

import scala.annotation.tailrec

object Consecutive_three {

  def recusivefindofthree(arr:Array[Int]) : Boolean = {
    @tailrec
    def go(arr:Array[Int],n:Int)  : Boolean  = {
    if(n == arr.length-1) false
    else if(arr(n) == arr(n+1) && (arr(n),arr(n+1)) == (3,3)) true
    else go(arr,n+1)
    }
    go(arr,0)
  }

  def main(args: Array[String]): Unit = {
    val arr = Array.fill(scala.io.StdIn.readInt()) {
      scala.io.StdIn.readInt()
    }
    println(recusivefindofthree(arr))
  }

}
