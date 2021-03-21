package com.story.scala.study

case class Dog(name: String, age: Int) {

}

object Study05 {
  def main(args: Array[String]): Unit = {
    val dog1 = new Dog("哈士奇", 1)
    val dog2 = new Dog("哈士奇", 1)
    println(dog1.equals(dog2))
    println(dog1 == dog2)
  }
}
