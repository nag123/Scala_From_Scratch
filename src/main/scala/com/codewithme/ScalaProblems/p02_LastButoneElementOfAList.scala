package com.codewithme.ScalaProblems

//P02 (*) Find the last but one element of a list.
//     Example:
//     scala> penultimate(List(1, 1, 2, 3, 5, 8))
//     res0: Int = 5
object p02_LastButoneElementOfAList extends App{

  def penultimate[A](list: List[A]) : A = list match {
    case h :: List(t) => h
    case _ :: tail => penultimate(tail)
    case _ => throw new NoSuchElementException
  }
println("The result is ::: "+penultimate(List(1, 1, 2, 3, 5, 8)))
  println("The result is ::: "+penultimate(List(32,64)))
  println("The result is ::: "+penultimate(List(42)))
  println("The result is ::: "+penultimate(List()))
}

/*SCENARIO 1 : INPUT : List(1, 1, 2, 3, 5, 8)
* FIRST TIME ->
* Pattern: case h :: List(t) => h
Explanation: The pattern does not match because the input list has more than two elements.
*  It's not in the form head :: List(single-element-tail).
*
* case _ :: tail => penultimate(tail)
* Here it gives List(1,2,3,5,8) leaving the head -> 1
*
* 2ND ITERATION
* case _ :: tail => penultimate(tail)
* Here it gives List(2,3,5,8) leaving the head -> 1
*
* 3RD ITERATION
* case _ :: tail => penultimate(tail)
* Here it gives List(3,5,8) leaving the head -> 2
*
* 4TH ITERATION
* case _ :: tail => penultimate(tail)
* Here it gives List(5,8) leaving the head -> 3
*
*
* 5TH ITERATION
* Pattern: case h :: List(t) => h
* Here it takes head 5 leaving the tail -> 8
*
* SO THE OUTPUT IS 5
* */
