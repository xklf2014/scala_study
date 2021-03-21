package com.story.spark.api

import org.apache.spark.{SparkConf, SparkContext}

object SparkApiStudy01 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("testapi")
    val sc = new SparkContext(conf)

    val data = sc.parallelize(List(1,2,3,4,5,6,7,6,5,4,4,3,2,1))
    val ints = data.filter(_>3)
    val res = ints.collect()
    res.foreach(println)

    //去重操作 -> 分组->聚合->获取key
    val distinctData = data.map((_,1)).reduceByKey(_+_).map(_._1)
    distinctData.foreach(println)

    println("------------------")

    val distinctData2 = data.distinct()
    distinctData2.foreach(println)


  }

}
