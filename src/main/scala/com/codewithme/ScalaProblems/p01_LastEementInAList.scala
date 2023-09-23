
package com.codewithme.ScalaProblems
// P01 (*) Find the last element of a list.
//     Example:
//     scala> last(List(1, 1, 2, 3, 5, 8))
//     res0: Int = 8



object p01_LastEementInAList extends App {

  println(last(List("D","B","a","A")))
  println(last(List(1, 1, 2, 3, 5, 8)))

  def last[A](list: List[A]): A = {
    return list.last
  }


}
