package com.codewithme.ScalaProblems

object p04_FindNoOfElementInAList extends App{
  var elementLength = List(1,2,3,4,5,6).length //Normal way
println(List(Nil).length)
  //Procedural Solution
  def length[A](l:List[A]) : Int = length(l)

  //Recursive
  def lengthFindingRecursively[A](l:List[A]):Int = l match {
    case Nil => 0
    case h :: tail => 1 + lengthFindingRecursively(tail)
  }
//using folding concept
  def lengthFindingUsingFolding[A](l : List[A]) : Int = l.foldLeft(0){(c,_) => c+1}

  //How it works ? List(1,3,2,4,5,6).foldLeft(0,1) => 0+1 = 1 . c = 1
  //l.foldLeft(0){(1,3) => 1+1 = 2 . c  = 2
  //So basically the foldLeft() method visits each element of the list from left to right
  // and applies a function passing the result of the previous application.
  // The first element receives the initial value.



}
