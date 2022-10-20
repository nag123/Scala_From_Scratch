package com.codewithme.functionalprogramming.GettingStarted

import scala.annotation.elidable

object PassingFunctionToAFunction {

  def factorial(n : Int):Int = {
@annotation.tailrec
    def go(n:Int , acc : Int): Int = {
      if(n<=0)acc else go(n-1,n*acc)
    }
    go(n,1) // We should define the go in the end of the factorial method since the output of factorial method is an int
  }


    def main(args: Array[String]): Unit = {
       println(factorial(5))

    }
  }


