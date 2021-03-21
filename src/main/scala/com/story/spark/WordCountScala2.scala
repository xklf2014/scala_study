package com.story.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCountScala2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("worldcount")
    conf.setMaster("local")

    val sc = new SparkContext(conf)
    val fileRdd = sc.textFile("data/testdata.txt")
    val words = fileRdd.flatMap((s: String) => {
      s.split(" ")
    })
    val rs = words.map((_, 1))

    val result = rs.reduceByKey((x: Int, y: Int) => {
      x + y
    })

    val reverse = result.map((x) => {
      (x._2, 1)
    })
    val finalRs = reverse.reduceByKey(_ + _)
    finalRs.foreach(println)

  }
}
