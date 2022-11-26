package com.codewithme.functionalprogramming.GettingStarted

object FPCoreConcepts {
  //partial function


  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = {
    // return a function that takes a B and returns a C
    //(b: B) => f(a,b)/* Somehow get a value of type C here */
    f(a, _)
  }

  /**
   * Currying in Scala is simply a technique or a process of transforming a function.
   * This function takes multiple arguments into a function that takes single argument.
   * It is applied widely in multiple functional languages.
   *
   * Syntax : def functionname(argument1, argument2) = operation
   * */
  def curry[A,B,C](f: (A, B) => C): A => (B => C) = {
    (a:A) => (b:B) => f(a,b)
  }

  def add(x:Int):Int => Int = y => x+y
  def uncurry[A,B,C](f: A => B => C): (A, B) => C = {
    (a,b) => f(a)(b)
  }

  val sum = (a:Int) => (b:Int) => a + b


  def main(args: Array[String]): Unit = {

    val multiplyByTwo = partial1(2, (a: Int, b: Int) => a * b)
    println(multiplyByTwo(3))

    val c = curry((a:Int, b:Int) => a == b)
    println("1 == 2? ", c(1)(2))
    println("2 == 2? ", c(2)(2))

    val c_partial = c(1)

    println("[partial] 1 == 2? ", c_partial(2))
    println("[partial] 1 == 1? ", c_partial(1))

    val uncurried = uncurry[Int, Int, Int](sum)
    println("sum(1)(2)", sum(1)(2))
    println("uncurry(1,2)", uncurried(1,2))

  }
}
