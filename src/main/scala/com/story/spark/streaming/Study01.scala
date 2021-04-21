package com.story.spark.streaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Study01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("streaming").setMaster("local[5]")
    val context = new StreamingContext(conf,Seconds(5)) //触发拉取数据时间间隔
    context.sparkContext.setLogLevel("ERROR")

    val dataSource = context.socketTextStream("localhost",8889)
/*    val flatMap = dataSource.flatMap(_.split(" "))
    val resDStream = flatMap.map((_,1)).reduceByKey(_+_)
    resDStream.print()*/

    val res = dataSource.map(_.split(" ")).map(vars => (vars(0),vars(1)))
    res.print()



    context.start()
    context.awaitTermination()

  }
}
