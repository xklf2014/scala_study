package com.story.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Study02 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("streaming").setMaster("local[5]")
    val sc = new StreamingContext(conf,Seconds(5)) //触发拉取数据时间间隔
    sc.sparkContext.setLogLevel("ERROR")

    val dataSource = sc.receiverStream(new CustomerReceiver("localhost",8889))
    dataSource.print()

    sc.start()
    sc.awaitTermination()

  }
}
