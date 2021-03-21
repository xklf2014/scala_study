package com.story.scala.study

trait God{
  def say(): Unit ={
    println("god say")
  }
}

trait Monster{
  def sneer(): Unit ={
    println("monster sneer")
  }

  def attact():Unit
}

class Person(name:String) extends God with Monster {
  def hello(): Unit = {
    println(s"$name say hello")
  }

  override def attact(): Unit = {
    println("自己实现的")
  }
}


object Study04 {
  def main(args: Array[String]): Unit = {
    val p = new Person("zhangsan")
    val p1:God = new Person("zhangsan")
    p.hello()
    p.say()
    p.sneer()
    p.attact()

    p1.say()

}
}
