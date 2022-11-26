 var i  = 5

 print(i.+(1))
 println(i.-(1))

 var acc: Map[Char,Int] = Map('S' -> 1,'c' -> 1,'a' -> 1,'l' -> 1)

 //var currentChar = acc += ('a'-> 1)
 val currentOccurrences = acc('a')
 acc = Map('a' -> (currentOccurrences + 1))
 println(acc)
var string = "()"
 val (before, after) = string.splitAt(1)



// +" " +  + " " ++i + " " + --i +  i

 class PlusPlusInt(i: Int){
  def ++ = i+1
 }

  def int2PlusPlusInt(i: Int) = new PlusPlusInt(i)

 val a = int2PlusPlusInt(5).++
 println("The value of a is :::: "+a)


 var list1 = List(10, 20, 30, 40, 50)

 for( i <- 0 until list1.length) {
  println(list1.length-i)
 }

 /** Higher order functions are the ones which takes other function
  * as a arguments and may return functions as their output.
 */

/*
* a higher-order function for doing what is called
partial application.
* This function, partial1, takes a value and a function of two
arguments, and returns a function of one argument as its result.
* The name comes from the fact that the function is being applied to some but not all of its required
arguments.
* */
 def partial1[A,B,C](a: A, f: (A,B) => C): B => C