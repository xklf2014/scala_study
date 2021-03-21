package com.story.scala.study

//match
object Study06 {
  def main(args: Array[String]): Unit = {
    val tup: (Double, Int, String, Boolean, Char) = (51.0, 88, "abc", true, 'a')
    val iter = tup.productIterator

    val res = iter.map((x) => {
      x match {
        case 88 => println(s"$x is int")
        case d: Double if d>50 => println(s"$d is double and greater than 50")
        case "abc" => println(s"$x is String")
        case true => println(s"$x is boolean")
        case 'a' => println(s"$x is char")
        case _ => println("invaild type")
      }
    })

    while(res.hasNext)println(res.next())
  }

}
