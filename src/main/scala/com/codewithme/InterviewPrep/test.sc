import scala.collection.immutable.ListMap

var input = Seq(1,1,1,3,2,2,3,4,4,5,6,5,6)
var s = input.groupBy(identity)
  var b=s.mapValues(_.size)
var res = ListMap(b.toSeq.sortBy(_._1):_*)
print(res)

def isValid(s : String) : Boolean = {
  //map for easy comparison
  var matchingPairs =
    Map(
    ')' -> '(',
    '}' -> '{',
    ']' -> '[',
    '>' -> '<'
    )
    //stack to keep track of open bracket
    val stack = scala.collection.mutable.Stack[Char]()
    //traverse each character in the input string
   for(ch <- s)
      {
        if(matchingPairs.values.toSet.contains(ch)){
          //If there is a opening push it to the stack
          println("Inside if ::: ch value is ::: "+ch)
          stack.push(ch)
      }
        else if(matchingPairs.contains(ch))
          {
            println("The stack is ::: "+stack)
            println("matchingPairs(ch) :::::"+matchingPairs(ch))
            println("stack(pop) :::::"+stack.pop())
            if(stack.isEmpty || stack.pop() != matchingPairs(ch))
              {
                return false
              }
          }
  }
  stack.isEmpty
}

//println(isValid("({})"))       //true
//println(isValid("){}("))      //false
//println(isValid("(){}[]"))   //true
//println(isValid("{(({)})}")) //false
println(isValid("}]"))       //false