package com.codewithme.InterviewPrep

import com.codewithme.InterviewPrep.CountTheNoOfCharacter.countCharacters

object Anagram {

  def checkForNoofCharacterEquality(sa:String,sb : String):Boolean = countCharacters(sa) == countCharacters(sb)
  def checkForsortedEquality(sa:String,sb : String):Boolean = sa.sorted == sb.sorted

  def testCheckAnagrams() = {
    println(checkForNoofCharacterEquality("desserts", "stressed"))
    println(checkForNoofCharacterEquality("Nax", "Hari"))
    println(checkForsortedEquality("desserts", "stressed"))
    println(checkForsortedEquality("Nax", "Hari"))
  }

  def main(args: Array[String]): Unit = {
    testCheckAnagrams()
  }

}
