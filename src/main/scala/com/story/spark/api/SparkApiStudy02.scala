package com.story.spark.api

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkApiStudy02 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("testapi")
    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(List(1, 2, 3, 4, 5))
    val rdd2 = sc.parallelize(List(4, 5, 6, 7, 8))

    //myunion(rdd1,rdd2)
    //mycartesian(rdd1,rdd2)
    //myintersection(rdd1,rdd2)
    //差集存在方向性
    /*mysubtract(rdd1,rdd2)
    mysubtract(rdd2,rdd1)*/

    val kv1 = sc.parallelize(List(("zhangsan", 10), ("zhangsan", 20), ("lisi", 30), ("wangwu", 50)))
    val kv2 = sc.parallelize(List(("zhangsan", 60), ("zhangsan", 70), ("zhaoliu", 80), ("liuqi", 100)))
    //mycogroup(kv1,kv2)
    //myjoin(kv1,kv2)
    //myLeftOuterJoin(kv1,kv2)
    //myRightOuterJoin(kv1,kv2)
    //myFullOuterJoin(kv1,kv2)

    while (true) {

    }

  }

  def myunion[T](rd1: RDD[T], rd2: RDD[T]): Unit = {
    println(rd1.partitions.size)
    println(rd2.partitions.size)

    val unionRdd = rd1.union(rd2)
    println(unionRdd.partitions.size)

    unionRdd.foreach(println)
  }

  def mycartesian(rd1: RDD[Int], rd2: RDD[Int]) {
    val cartesianRdd = rd1.cartesian(rd2)
    println(rd1.partitions.size)
    println(rd2.partitions.size)
    cartesianRdd.foreach(println)
  }

  def myintersection(rd1: RDD[Int], rd2: RDD[Int]): Unit = {
    val inter = rd1.intersection(rd2)
    inter.foreach(println)
  }

  def mysubtract(rd1: RDD[Int], rd2: RDD[Int]): Unit = {
    val sub = rd1.subtract(rd2)
    sub.foreach(println)
  }

  def mycogroup(kv1: RDD[(String, Int)], kv2: RDD[(String, Int)]) {
    val rs = kv1.cogroup(kv2)
    rs.foreach(println)
  }

  def myjoin(kv1: RDD[(String, Int)], kv2: RDD[(String, Int)]) {
    val rs = kv1.join(kv2)
    rs.foreach(println)
  }

  def myLeftOuterJoin(kv1: RDD[(String, Int)], kv2: RDD[(String, Int)]): Unit = {
    val rs = kv1.leftOuterJoin(kv2)
    rs.foreach(println)
  }

  def myRightOuterJoin(kv1: RDD[(String, Int)], kv2: RDD[(String, Int)]): Unit ={
    val rs = kv1.rightOuterJoin(kv2)
    rs.foreach(println)
  }

  def myFullOuterJoin(kv1: RDD[(String, Int)], kv2: RDD[(String, Int)]){
    val rs = kv1.fullOuterJoin(kv2)
    rs.foreach(println)
  }

}
