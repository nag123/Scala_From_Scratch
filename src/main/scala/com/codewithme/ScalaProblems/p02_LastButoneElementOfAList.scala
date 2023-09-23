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
}
