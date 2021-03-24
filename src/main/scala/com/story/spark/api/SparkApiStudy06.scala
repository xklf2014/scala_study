package com.story.spark.api

import org.apache.spark.{SparkConf, SparkContext}

object SparkApiStudy06 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("partitions")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val data = sc.parallelize(1 to 100)
    data.sample(true,0.1,666).foreach(println)
    println("--------------------------------------")
    data.sample(true,0.1,666).foreach(println)
    println("--------------------------------------")
    data.sample(false,0.1,888).foreach(println)
    println("--------------------------------------")


    val data1 = sc.parallelize(1 to 10,5)
    println(s"data   --- ${data1.getNumPartitions}")
    val data2 = data1.mapPartitionsWithIndex(
      (pi,pt)=>{
        pt.map(e=>(pi,e))
      }
    )
    data2.foreach(println)

    val newdata = data2.repartition(4)
    println(s"data   --- ${newdata.getNumPartitions}")
    val res = newdata.mapPartitionsWithIndex(
      (pi,pt)=>{
        pt.map(e=>(pi,e))
      }
    )
    res.foreach(println)

    val data3 = sc.parallelize(1 to 10,5)
    data3.coalesce(8,false)//分区从小变大，不通过shuffle是不能进行重新分区
    data3.coalesce(3,false)//分区从大变小，不通过shuffle是走io数据移动
    data3.coalesce(8,true)//shuffle为true，无论大变小，还是小变大都可以正确分区

  }

}
