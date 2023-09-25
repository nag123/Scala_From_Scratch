package com.codewithme.ScalaProblems
// P03 (*) Find the Kth element of a list.
//     By convention, the first element in the list is element 0.
//
//     Example:
//     scala> nth(2, List(1, 1, 2, 3, 5, 8))
//     res0: Int = 2

object p03_FindTheKthElementOfList extends App{
  def KthElementInList[A](k:Int,l:List[A]) : A = {
    if(k>=0 && k <=l.length) l(k)
    else throw new NoSuchElementException
  }
}
