package com.codewithme.functionalprogramming.GettingStarted

object LetsLearnAboutMethodDefinition {

  //This method takes a int variable and returns a integer variable
  def abs(n : Int):Int={
    //If  the n variable is less than 0 say -4 we are making it as -(-4) which is +4 else we are returning 4
    if(n<0)-n else n
  }
  //This method take a integer value and returns a unit value . Since we didnt specify the return type of the below method
  private def formatAbsol(x : Int)={
  //val msg = "The absolute value of %d is %d".format(x,abs(x))
    val msg = s"The absolute value of $x is ${abs(x)}" //String interpolation
    msg
    //If i didnt return the msg in the line 13 the output is unit so () will be pass to the main method and it is  printed
  }


  def main(args:Array[String])={
    println(formatAbsol(-54))
    /* Here
    * 1. the main method calls the formatAbsol method
    * 2. The formatAbsol method calls the abs method , the return from the abs method is a int
    * 3. The integer result which is -(-42) passed to formatAbsol
    * 4. The unit result such as the message : The absolute value of -42  is 42 is passed to main method
    * 5. And the result is printed
    */
  }
}
