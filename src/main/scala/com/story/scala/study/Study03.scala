package com.story.scala.study

import java.util

import scala.collection.mutable.ListBuffer

object Study03 {
  def main(args: Array[String]): Unit = {
      val strings = new util.LinkedList[String]()
      strings.add("hello");strings.add(1,"scala")
      println(strings)

      val arr01 = Array(1,2,3,4)
      println(arr01(1)) // 数组下标用小括号

      arr01(1) = 10
      //遍历,需要函数接收元素
      arr01.foreach(println)

    val list01 = List(1,2,3,4,5,4,3,2)
    println(list01)

    val list02 = new ListBuffer[Int]()
    list02.+= (33)
    list02.+= (34)
    list02.+= (35)

    list02.foreach(println)
    println("---------set-----------")

    val set01 = Set(1,1,1,1,2,3,4,5,4,3,2,1)
    for (elem <- set01) {
        println(elem)
    }

    set01.foreach(println)

    import scala.collection.mutable.Set
    val set02 = Set(11,22,33,44,11)
    set02.add(100)
    set02.foreach(println)

    val set03 = scala.collection.immutable.Set(11,22,33,44)

    val t2 = new Tuple2(11,"hello")
    val t3 = Tuple3(11,22,"hahaha")

    println(t2._1,t3._3)

    val t5 = ((a:Int,b:Int)=>a+b+10,1,2,3,4)
    println(t5._1(5,5))
    println(t5._1)


    val iterator = t5.productIterator
    while(iterator.hasNext){
      println(iterator.next())
    }


    println("--------map-------")
    val map01 = Map(("a",10),"b"->22,("c",3333),("a",2222))
    println(map01)
    println(map01.get("a")) //获取存在的key，返回Some
    println(map01.get("d")) //获取不存在的key，返回None
    println(map01.getOrElse("a","null"))
    println(map01.getOrElse("d","null"))

    val keys = map01.keys
    for (elem <- keys) {
      println(s"key :$elem and value : ${map01.get(elem).get}")
    }

    val map02 = scala.collection.mutable.Map(("a",1),("b",2))
    map02.put("c",3)
    map02.put("c",4)

    val l1 = List(1,2,3,4,5,6)
    val listMap = l1.map((x:Int)=> x<<1)
    for (elem <- listMap) {
      println(elem)
    }

    val listMap2 = l1.map( _*10)
    println(listMap2)

    val listStr = List("hello scala","is one","of the","best language","hello")
    //val listStr = Array("hello scala","is one","of the","best language","hello")
    //val listStr = Set("hello scala","is one","of the","best language","hello")

    val flatMap = listStr.flatMap((x:String)=> x.split(" "))
    flatMap.foreach(println)
    val rsMap = flatMap.map((_,1))
    println(rsMap)

    println("----------------------")
    val iter = listStr.iterator
    val iterFlatMap  = iter.flatMap((x:String)=> x.split(" "))
    val iterMapList = iterFlatMap.map((_,1))

    //iterMapList.foreach(println)
    while(iterMapList.hasNext){
      println(iterMapList.next())
    }







  }

}
