package com.story.scala.study

import java.util

//隐式转换
object Study08 {
  def main(args: Array[String]): Unit = {
    val list = new util.LinkedList[Int]()
    list.add(1)
    list.add(2)
    list.add(3)



    /*    def foreach[T](list:util.LinkedList[T],f:(T)=>Unit): Unit ={
          val iter:util.Iterator[T] = list.iterator()
          while(iter.hasNext) f(iter.next())

        }

        foreach(list,println)*/

    //val l = new Chage(list)
    //l.foreach(println)

    /*    implicit def toAnthorType[T](list:util.LinkedList[T]): Chage[T] ={
          new Chage(list)
        }*/

    implicit class Chage[T](list: util.LinkedList[T]) {
      def foreach(f: (T) => Unit): Unit = {
        val iter: util.Iterator[T] = list.iterator()
        while (iter.hasNext) f(iter.next())
      }
    }
    val newlist = new Chage(list)
    newlist.foreach(println)

    val linkedList = new util.LinkedList[Int]()
    linkedList.add(11)
    linkedList.add(22)
    linkedList.add(33)

    val arrayList = new util.ArrayList[Int]()
    arrayList.add(111)
    arrayList.add(222)
    arrayList.add(333)

    implicit def toLinkedList[T](list: util.LinkedList[T]) ={
      val it: util.Iterator[T] = list.iterator()
      new OutChage(it)
    }

    implicit def toArrayList[T](list: java.util.ArrayList[T]) ={
      val it: util.Iterator[T] = list.iterator()
      new OutChage(it)
    }


    arrayList.foreach(println)

    implicit val hahaah = "lisi" //隐私转换，如果定义再参数列表，要么需要明确给定参数值，要么全部通过隐私转换进行传参
    //implicit val xixi = "wangwu"   //could not find implicit value for parameter name: String
    def printName(implicit name:String): Unit ={
      println(name)
    }

    printName("zhangsan")
    printName


    implicit val age = 30
    def printNameAndAge(name:String)(implicit age:Int): Unit ={
      println(s"name : ${name} , age : ${age}")
    }

    printNameAndAge("wangwu")
  }

}

/*class Chage[T](list: util.LinkedList[T]) {
  def foreach(f: (T) => Unit): Unit = {
    val iter: util.Iterator[T] = list.iterator()
    while (iter.hasNext) f(iter.next())
  }
}*/

class OutChage[T](iter: util.Iterator[T]) {
  def foreach(f: (T) => Unit): Unit = {
    while (iter.hasNext) f(iter.next())
  }
}