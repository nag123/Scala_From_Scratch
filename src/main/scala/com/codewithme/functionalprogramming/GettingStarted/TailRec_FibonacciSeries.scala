package com.codewithme.functionalprogramming.GettingStarted

object TailRec_FibonacciSeries {


    def FibonacciSeries(n: Int): Int = {

      def goto(n: Int): Int = {
        n match {
          case 0 => 0
          case 1 => 1
          case _ => goto(n - 2) + goto(n - 1)
        }

      }

      goto(n)
    }

    def fiboTailRecursive(n: Int): Int = {
      @annotation.tailrec
      def recursion(n: Int, a: Int, b: Int): Int = {
        if (n > 0) recursion(n - 1, b, a + b)
        else a
      }

      recursion(n, 0, 1)
    }


    def main(args: Array[String]): Unit = {
      //Fn = Fn-1 + Fn-2 with seed value F0 = 0 and F1 = 1.
      println(FibonacciSeries(5))
      var sum = 0
      for (i <- 0 until 5) {
        sum = sum + FibonacciSeries(i)
        print(FibonacciSeries(i) + " ")
      }
      println()
      println("The sum of the total fibo values are :::: " + sum)
    }
  }
