package com.story.scala

object TestScala {

  private val cons = new Cons1(1)

  var name = "zhangsan"
  println("static area print")
  private val h = new Hello

  private val h1 = new Hello(20)

  def main(args: Array[String]): Unit = {
    h.printMsg()
    println(s"this is hello's var : "+h.a)

    cons.run()
}
  println("static area down print")

}

class Hello{
  var a = 3
  var i : Int = 5
  //var ： 变量   val : 产量
  val b = 5

  val age = 40

  println(s"hello....$a....")

  def printMsg() :Unit ={
    println(s"print msg ${TestScala.name}")
  }

  println(s"hahaha ${a+4}")

  def this(age:Int) {
    this()
    println("age is "+age)
  }
}

//类名构造器中的参数是类的成员属性，默认为val，而且是private
class Cons(score : String){

  println(score)

}

//只有再类名构造器中改掉参数可以设置为var，其它函数中的参数都val，且不可用被修改为var
class Cons1(var gender:String){
  gender = "girl"
  def f(age:Int): Unit ={
    println(age) // 此处为val
  }

  def this(name:Int) {
    this("woman")
  }

  def run(): Unit = {
    println(s"gender is ${gender}" )
  }
}