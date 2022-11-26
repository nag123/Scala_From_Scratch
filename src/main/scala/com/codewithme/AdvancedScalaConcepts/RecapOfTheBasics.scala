package com.codewithme.AdvancedScalaConcepts

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.util.{Failure, Success}

object RecapOfTheBasics {

  def main(args : Array[String]):Unit = {
    val aCondition : Boolean = false
    //Expression
    val aConditionVal =  if(aCondition) 42 else 65

    //instructions vs expressions

    val aCodeBlock = {
      if(aCondition) 42
      56
    }
  }

  def myFunc(x : Int) = 42

  //OOPS
  class Animal
  class Dog extends Animal
  trait Carnivore { def eat(animal : Animal) :Unit }

  class Crocodile extends Animal with Carnivore{
    override def eat(animal : Animal) = println("Crunch!!!")
  }

  //singleton class
  object mySingleTon

  //Companion object
  object Carnivore

  //Generics
  trait myList[A]

  //method notation
  val x = 1+2
  val y = 1.+(2)

  //Functional programming
  val incrementer : Int => Int = x => x+1
  val incremented = incrementer(42)


  //map , filter , flatmap
  val processedList = List(1,2,3).map(incrementer)

  //Pattern matching
  val unknown : Any = 45
  val ordinal = unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  //try catch
  try{
    throw new NullPointerException
  }catch{
    case _: NullPointerException => "Some returned value"
    case _=>"Something else"
  }

  //Future
  import scala.concurrent.ExecutionContext.Implicits.global
  val aFuture = Future {
    42
  }

aFuture.onComplete{
  case Success(meaningoflife) =>println(s"I've found $meaningoflife")
  case Failure(ex) => "Something else"
}

  //partial functions
  val aPartialFunction = (x : Int) => x match {
    case 1 => 43
    case _ => 999
  }

  //implicits

  //auto injection by the compiler
  def methodwithImplicitArgument(implicit x : Int) = x+43
  implicit val implicitInt = 43
  val implicitCall = methodwithImplicitArgument

  //implicit defs
  case class Person(name : String){
    def greet = println(s"my name is $name")
    }

  implicit def fromStringToPerson(name : String) = Person(name)
  "Nax".greet //fromStringToPerson("Nax").greet

  //implicit class
  implicit class Doggie(name : String){
    def bark = println("bark!!!")
  }
  "Hari".bark

  @tailrec def factorial(n : Int,acc:Int):Int =
    if(n <=0) acc else factorial(n-1,n*acc)

  var fact = factorial(5,1)


}
