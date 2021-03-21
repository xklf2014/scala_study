package com.story.scala.study

object Study07 {
  def main(args: Array[String]): Unit = {
    def typeof: PartialFunction[Any, String] = {
      case "hello" => "val is hello"
      case x:Int => s"${x} is int"
      case _ => "none"
    }


    println(typeof("hello"))
    println(typeof(111))
    println(typeof("hi"))
  }

}
