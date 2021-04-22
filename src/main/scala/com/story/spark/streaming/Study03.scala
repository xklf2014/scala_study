package com.story.spark.streaming

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Study03 {
  def main(args: Array[String]): Unit = {

    // spark streaming 100ms batch
    val conf = new SparkConf().setMaster("local[8]").setAppName("test")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Duration(1000))

    val resource = ssc.socketTextStream("localhost", 8889)
    /*val formated = resource.map(_.split(" ")).map(x=> (x(0),x(1).toInt))
    val resBatch = formated.reduceByKey(_+_)
    resBatch.mapPartitions(iter => {println("1s");iter}).print()

    val newDs = formated.window(Duration(5000),Duration(5000)) //第二个参数为步长（滑动距离），代表job启动间隔
    val res02Batch = newDs.reduceByKey(_+_)
    res02Batch.mapPartitions(iter => {println("5s");iter}).print()*/

    /*    查看历史5s内的数据
        val formated = resource.map(_.split(" ")).map(x=> (x(0),1))
        val batchDs = formated.window(Duration(5000),Duration(1000)).reduceByKey(_+_)
        batchDs.print()*/

    /*val formated = resource.map(_.split(" ")).map(x=> (x(0),1))
    formated.reduceByKeyAndWindow(_+_,Duration(5000)).print()*/


    val formated = resource.map(_.split(" ")).map(x => (x(0), x(1).toInt))
    /*  中途加工
    val res = formated.transform(
      rdd => {
          rdd.map(x => (x._1,x._2 * 10))
      }
    )
    res.print()
    */

    /* 末端处理
    formated.foreachRDD(
      rdd => {
        rdd.foreach(println)
      }
    )*/

    //val bc = sc.broadcast((1 to 5 ).toList)

    /*
    *  作用域:
    *  application
    *  job
    *  task
    *
    */
    var bc:Broadcast[List[Int]] = null
    var jobNum = 0
    println("application")
    val res = formated.transform(

      rdd => {
        println(s"job ${jobNum}" )
        jobNum += 1
        if(jobNum < 5){
          bc = sc.broadcast((1 to 5 ).toList)
        }else{
          bc = sc.broadcast((6 to 15 ).toList)
        }

        rdd.filter(x => bc.value.contains(x._2))
      }
    )

    res.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
