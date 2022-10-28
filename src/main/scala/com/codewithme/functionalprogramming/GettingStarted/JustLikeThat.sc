 var i  = 5

 print(i.+(1))
 println(i.-(1))


// +" " +  + " " ++i + " " + --i +  i

 class PlusPlusInt(i: Int){
  def ++ = i+1
 }

  def int2PlusPlusInt(i: Int) = new PlusPlusInt(i)

 val a = int2PlusPlusInt(5).++
 println("The value of a is :::: "+a)

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