package com.codewithme.InterviewPrep

object ClosestToDefault extends App{

  def findClosestNumber(nums: Array[Int]): Int = {
    var closest = nums(0)
    for(num <- nums){
      if(num.abs < closest.abs) closest = num else if(num.abs == closest.abs && num>closest ) closest = num
    }
  closest
  }

  var inputs = Array(-100000,-100000)
println(findClosestNumber(inputs))
}
