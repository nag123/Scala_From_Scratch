package com.codewithme.functionalprogramming.GettingStarted

object Annonymous_Function {

  def formatResult(name: String, n: Int, f: Int => Int) = {
    val msg = "The %s of %d is %d."
    msg.format(name,n, f(n))
  }

  def abs(n : Int) : Int = {
    if(n < 0) -n else n
  }

  def factorial(n : Int):Int = {
    @annotation.tailrec
    def go(n : Int,acc : Int) : Int = {
      if(n<=0) acc else go(n-1,n*acc)
    }
    go(n,1)
  }
  def main(args: Array[String]): Unit = {
    println(formatResult("absolute value", -42, abs))
    println(formatResult("factorial", 5, factorial))
    println(formatResult("increment", 7, (x: Int) => x + 1))
    println(formatResult("increment2", 7, (x) => x + 1))
    println(formatResult("increment3", 7, x => x + 1))
    println(formatResult("increment4", 7, _ + 1))
    println(formatResult("increment5", 7, x => { val r = x + 1; r }))
  }
}
