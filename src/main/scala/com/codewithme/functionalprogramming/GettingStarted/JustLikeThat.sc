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