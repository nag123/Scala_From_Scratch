package com.codewithme.functionalprogramming.GettingStarted

/**
 * KEY TERMS :
 * Referential transparency enables a mode of reasoning about program evaluation called the substitution model

 * COMPOSITION :
 * Function composition is a way in which a function is mixed with other functions.
 * During the composition the one function holds the reference to another function in order to fulfill it's mission
 *
 *  we say that computation proceeds by
 *  substituting equals for equals.
 *  In other words, RT enables equational reasoning about programs.

 * */



/**
 * FEW TAKEAWAYS
 * Dont give it as println(declareTheWinner(naxi,sivi))
 * WHY ?  Because the method declareTheWinner returns a unit so the output will be ()
 * OTHER WISE : declareTheWinner calls the method printTheWinner method which prints the message in it
 *
 *
 * */

case class Player(Name:String,Score:Int)
/* METHOD 1
def printTheWinner(p:Player): Unit ={
  println(s"${p.Name} is the winner")
}

//This gives the output as The result is ::::: ()
def declareTheWinner(p1:Player,p2:Player):Unit = {
  if(p1.Score > p2.Score) printTheWinner(p1) else printTheWinner(p2)
}
*/

/* METHOD 2
  separates the logic of computing the winner from the displaying of the result
Computing the winner in winner is referentially transparent and the
impure part—displaying the result—is kept separate in printWinner.
We can now reuse the logic of winner to compute the winner among a list of players */



object PureFunction {

  def printTheWinner(p:Player): Unit ={
    println(s"${p.Name} is the winner")
  }

  //A pure function that takes two players and returns the higher-scoring one.
  def Winner(p1:Player,p2:Player):Player={
    if (p1.Score > p2.Score) p1 else p2
  }

  def declareTheWinner(p1:Player,p2:Player):Unit = {
    printTheWinner(Winner(p1,p2))
  }

//Let us assume the player have 2 arguments such as the name and the score

  //main method doesnt return anything(Unit) but takes array as input which is a argument
  def main(args:Array[String]):Unit={
    println(":: In set 1  :::")
    val naxi = Player("Naxie",90)
    val sivi = Player("Sivi",100)
    declareTheWinner(naxi,sivi)

    println("::: In Set 2 ::: ")
    val players = List(Player("Sue", 7),
      Player("Bob", 8),
      Player("Joe", 4))

    printTheWinner(players.reduceLeft(Winner))

  }

}
