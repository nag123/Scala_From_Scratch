package com.codewithme.InterviewPrep


/*
Desired output:
  lesser_of_two_evens(2,4)---> 2
  lesser_of_two_evens(2,3)---> 3
  lesser_of_two_evens(3,4)---> 4
*/
object Lesser_Of_2_nos_Based_on_cond extends App{

def lesser_of_two_evens(a : Int,b: Int) :Int = {
  if(a%2 == 0 && b%2 == 0) Math.min(a,b) else Math.max(a,b)
}
  println(lesser_of_two_evens(2,4))
  println(lesser_of_two_evens(2,3))
  println(lesser_of_two_evens(3,4))

}
