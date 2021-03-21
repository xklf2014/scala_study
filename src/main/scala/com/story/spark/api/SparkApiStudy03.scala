package com.story.spark.api

import org.apache.spark.{SparkConf, SparkContext}

object SparkApiStudy03 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("testapi")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")


    val file = sc.textFile("data/pvuvdata.txt", 5)
    //43.169.217.152	河北	2018-11-12	1542011088714	3292380437528494072	www.dangdang.com	Login

    println("----------------PV-----------------")
    val pair = file.map(line => (line.split("\t")(5), 1))
    val reduce = pair.reduceByKey(_ + _)
    val map = reduce.map(_.swap)
    val sorted = map.sortByKey(false)
    val res = sorted.take(5)
    val pv = res.map(_.swap)
    pv.foreach(println)

    println("----------------UV-----------------")

    val keys = file.map(
      line => {
        val strs = line.split("\t")
        (strs(5), (strs(0)))
      }
    )

    val key = keys.distinct()
    val uvpair = key.map(k => (k._1,1))
    val uvreduce = uvpair.reduceByKey(_+_)
    val uvsorted = uvreduce.sortBy(_._2,false).take(5)
    uvsorted.foreach(println)

  }

}
