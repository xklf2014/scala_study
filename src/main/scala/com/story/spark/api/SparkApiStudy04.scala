package com.story.spark.api

import org.apache.spark.{SparkConf, SparkContext}

//聚合
object SparkApiStudy04 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("testapi")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val data = sc.parallelize(
      List(
        ("zhangsan", 10),
        ("zhangsan", 20),
        ("zhangsan", 30),
        ("lisi", 111),
        ("lisi", 222),
        ("wangwu", 33),
        ("wangwu", 44)
      )
    )

    val group = data.groupByKey()
    group.foreach(println)

    val res = group.flatMap(e => e._2.map(x => (e._1, x)).iterator)
    res.foreach(println)
    println("---------------")
    group.flatMapValues(e => e.iterator).foreach(println)
    println("---------------")

    group.mapValues(e => e.toList.sorted.take(2)).foreach(println)
    println("---------------")
    group.flatMapValues(e => e.toList.sorted.take(2)).foreach(println)
    println("---------------")

    val sum = data.reduceByKey(_ + _)
    sum.foreach(println)
    println("---------------")
    val max = data.reduceByKey((ov, nv) => if (ov > nv) ov else nv)
    max.foreach(println)
    println("---------------")
    val min = data.reduceByKey((ov, nv) => if (ov < nv) ov else nv)
    min.foreach(println)
    println("---------------")
    val count = data.mapValues(e => 1).reduceByKey(_ + _)
    count.foreach(println)
    println("---------------")

    val avg = sum.join(count).mapValues(e => e._1 / e._2)
    avg.foreach(println)
    println("---------------")

    val tmp = data.combineByKey(
      (value: Int) => (value, 1),
      (ov: (Int, Int), nv: Int) => (ov._1 + nv, ov._2 + 1),
      (v1: (Int, Int), v2: (Int, Int)) => (v1._1 + v2._1, v1._2 + v2._2)
    )

    tmp.mapValues(e => e._1/e._2).foreach(println)
  }
}
