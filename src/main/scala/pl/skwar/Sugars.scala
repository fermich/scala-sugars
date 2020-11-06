package pl.skwar

object Sugars {

  def toBeCurried(a: Int, b: String): Int = {
    println(s"a = ${a}")
    println(s"b = ${b}")
    666
  }

  def toBeCurriedImplicit(a: Int)(implicit b: String): Int = {
    println(s"a = ${a}")
    println(s"b = ${b}")
    666
  }

  def allImplicits(implicit a: Int, b: String): Int = {
    println(s"a = ${a}")
    println(s"b = ${b}")
    666
  }

  def callAllImplicits(): Int = {
    implicit val a: Int = 22
    implicit val b: String = "sss"

    allImplicits
  }


  /*
  The technique of converting a def method into a true function uses a Scala technology known as “Eta Expansion.”
   */
  def currying() = {
    // 1. Use 'Eta expansion' to turn a def method to Function2 instance: funName _
    // 2. Then create a 'curried' function from that Function2 instance by calling its 'curried' method:
    val functionInstance: (Int, String) => Int = (toBeCurried _)
    //without syntactic sugar: val functionInstance: Function2[Int, String, Int] = (toBeCurried _)
    //OK: functionInstance(1, "s")
    //FAIL: functionInstance(1)("s")

    val curriedFunction = functionInstance.curried
    //FAIL: curriedFunction(1, "a")
    //OK: curriedFunction(1)("2")

    println(functionInstance)
    val partiallyApplied = curriedFunction(2)
    println(partiallyApplied)
    val fullApplied = partiallyApplied("a")
    println(fullApplied)
  }

  def curryingWithImplicit() = {
    implicit val b: String = "a"
    val curriedFunction = toBeCurriedImplicit _
    println(curriedFunction)
    val restApplied = curriedFunction(2)
    println(restApplied)
  }

  //Will not work:
//  def curryingWithImplicit2() = {
//    implicit val a: Int = 2
//    val curriedFunction = toBeCurriedImplicit2("2")
//    println(curriedFunction)
//    val restApplied = curriedFunction(2)
//    print(restApplied)
//  }

  def main(args: Array[String]): Unit = {
    currying()
    curryingWithImplicit()
    callAllImplicits()
  }

}
