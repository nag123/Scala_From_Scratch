package com.codewithme.codinggames

import scala.io.StdIn.readLine
import scala.io.StdIn.readInt

/**
 * The while loop represents the game.
 * Each iteration represents a turn of the game
 * where you are given inputs (the heights of the mountains)
 * and where you have to print an output (the index of the mountain to fire on)
 * The inputs you are given are automatically updated according to your last actions.
 * */


/**
 * Explanation of the question according to the test cases:::
 * Ex : 9,6,8
 * Here the max is at index 0 so the output will be 0
 *
 * Ex : 0,6,8
 * Here the max is at the index 2 so the output will be 2
 *
 * Ex : 0,6,0
 * Here the max is at the index 1 so the output will be 1
 *
 * Since it is under a while loop the test cases go on in the coding games environment ...
 *
 * Hint : It is not necessary to follow the for loop that have been provided we can also get the input as we want .
 * The QUESTION HERE IS TO GET THE INDEX THATS IT
 *
 * I have tested with so many solution for this
 * */
object TheDescent extends App {

  // game loop - My solution
 while (true) {
    var max = 0
    var imax = 0
   //Here the for loop takes 0 to 7 values that is 8 inputs
    for (i <- 0 until 3) {
      val mountainH = readLine.toInt // represents the height of one mountain. - Ex - 9,6,8
      if (mountainH >= max) {
        max = mountainH
        imax = i
      }
    }

    // Write an action using println
    // To debug: Console.err.println("Debug messages...")

    println(imax) // The index of the mountain to fire on.
  }


}
