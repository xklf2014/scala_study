package com.story.spark.api

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer


object SparkApiStudy05 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("partitions")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val data = sc.parallelize(1 to 10 ,2)

    val res = data.mapPartitionsWithIndex(
      (pindex:Int,piter)=>{
          val lb = new ListBuffer[String]
         println(s"------------$pindex-----conn mysql------------")
        while(piter.hasNext){
          val value = piter.next()
          println(s"-----------${pindex}----select ${value}-------------")
          lb.+=(value+"selected")
        }

        println("-----------close mysql------------------")
        lb.iterator
      }
    )

    res.foreach(println)
    println("---------------------------------------------")

    val res02 = data.mapPartitionsWithIndex(
      (pindex: Int,piter)=>{
          new Iterator[String]{
            println(s"------${pindex}--conn mysql------")
            override def hasNext = if (piter.hasNext == false) {
              println(s"--------close  mysql--------"); false
            }else true

            override def next() = {
              val value = piter.next()
              println(s"-----------${pindex}----select ${value}-------------")
              value+"selected"
            }
          }

      }
    )

    res02.foreach(println)
  }
}
