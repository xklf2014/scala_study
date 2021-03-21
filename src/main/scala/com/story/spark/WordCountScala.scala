package com.story.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCountScala {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("worldcount")
    conf.setMaster("local")

    val sc = new SparkContext(conf)
    val fileRdd = sc.textFile("data/testdata.txt")
 /*   val words = fileRdd.flatMap((s:String)=>{s.split(" ")})
    val rs = words.map((_,1))

    val result = rs.reduceByKey((x:Int,y:Int)=>{x+y})

    result.foreach(println)*/

    fileRdd.flatMap(_.split(" "))
      .map((_,1)).
      reduceByKey(_+_)
      .foreach(println)
  }
}
