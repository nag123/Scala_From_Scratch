package com.codewithme.ScalaProblems
// P03 (*) Find the Kth element of a list.
//     By convention, the first element in the list is element 0.
//
//     Example:
//     scala> nth(2, List(1, 1, 2, 3, 5, 8))
//     res0: Int = 2

object p03_FindTheKthElementOfList extends App {
  def KthElementInList[A](k: Int, l: List[A]): A = {
    if (k >= 0 && k <= l.length)
      try {
        l(k)
      }
      catch {
        case e: IndexOutOfBoundsException =>
          println("NoSuchElementException its an indexoutofboundexception")
          throw new NoSuchElementException
      }
    else throw new NoSuchElementException
  }

  def KthElementInListRecursively[A](k:Int,l:List[A]) : A = k match {
    case 0 => l.head
    case k if k > 0 =>KthElementInListRecursively(k-1,l.tail)
    case _ => throw new NoSuchElementException
  }

  def KthElementInListRecursivelyFP[A](k:Int,l:List[A]) : A = (k,l) match {
    case (0,h :: _) => h
    case (k, _ :: tail) if k > 0 =>  KthElementInListRecursivelyFP(k-1,tail)
    case _ => throw new NoSuchElementException
  }
  def colorPrint() = println(fansi.Color.Magenta("This is printed in magenta color"))
colorPrint()
   println(KthElementInList(1, List(2,3,4)))
  println(KthElementInListRecursively(2, List(5,6,7,8)))
  println(KthElementInListRecursivelyFP(3, List(1,2,3,4)))
  println(KthElementInListRecursivelyFP(10, List(1,2,3,4)))
  println(KthElementInList(0, List())) //leads to error java.lang.IndexOutOfBoundsException: 0 lets handle it ussing recursive call

}