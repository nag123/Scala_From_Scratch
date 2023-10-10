package com.codewithme.ProblemSolving
/*
* Write a program to find the sum of the given series 1+2+3+ . . . . . .(N terms)
* Input:
N = 1
Output: 1
Explanation: For n = 1, sum will be 1.
*
* Input:
N = 5
Output: 15
Explanation: For n = 5, sum will be 15.
1 + 2 + 3 + 4 + 5 = 15.
*
* Your Task:
Complete the function seriesSum() which takes single integer n, as input parameters and returns an integer denoting the answer. You don't need to print the answer or take inputs.

Expected Time Complexity: O(1)
Expected Auxiliary Space: O(1)

Constraints:
1 <= N <= 10^5
* */
object SumOfArray extends App {
  def seriesSum(n : Int)  : Double = {
    var sum = 0.0
    for (i <- 0 to n)  sum +=i
    sum
  }

  println("The result is :::: "+seriesSum(5))
}
