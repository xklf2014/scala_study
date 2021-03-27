package com.story.spark.api

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.util.Sorting

object SparkApiStudy07 {
  val conf = new SparkConf().setMaster("local").setAppName("partitions")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")
  val file = sc.textFile("data/tempture.txt")
  val data = file.map(line => line.split("\t")).map(arr => {
    val arrs = arr(0).split("-")
    (arrs(0).toInt, arrs(1).toInt, arrs(2).toInt, arr(1).toInt)
  })

  /*  2019-6-1	39
    2019-5-21	33
    2019-6-1	38
    2019-6-2	31
    2018-3-11	18
    2018-4-23	22
    1970-8-23	23
    1970-8-8	32*/
  def main(args: Array[String]): Unit = {
    //topN1()
    //topN2()
    //topN3()
    //topN4()
    topN3new()

  }


  def topN1(): Unit = {


    val grouped = data.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()
    val res = grouped.mapValues(arr => {
      val map = new mutable.HashMap[Int, Int]()
      arr.foreach(
        x => {
          if (map.get(x._1).getOrElse(0) < x._2) {
            map.put(x._1, x._2)
          }
        }
      )

      arr.toList.sorted(new Ordering[(Int, Int)] {
        override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compareTo(x._2)
      })

    })

    res.foreach(println)
  }

  def topN2(): Unit = {

    implicit val autoSort = new Ordering[(Int, Int)] {
      override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compareTo(x._2)
    }


    val reduced = data.map(t4 => ((t4._1, t4._2, t4._3), t4._4)).reduceByKey((x: Int, y: Int) => if (y > x) y else x)
    val mapped = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
    val grouped = mapped.groupByKey()

    grouped.mapValues(arr => arr.toList.sorted.take(2)).foreach(println)

  }

  //最终结果存在问题,多级shuffle，后续分组不为前面的子集
  def topN3(): Unit = {
    val sorted = data.sortBy(t4 => (t4._1, t4._2, t4._4), false)
    val reduced = sorted.map(t4 => ((t4._1, t4._2, t4._3), t4._4)).reduceByKey((x: Int, y: Int) => if (y > x) y else x)
    val mapped = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
    val grouped = mapped.groupByKey()
    grouped.foreach(println)
  }

  def topN3new(): Unit = {
    val sorted = data.sortBy(t4 => (t4._1, t4._2, t4._4), false)
    val grouped = sorted.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()
    grouped.foreach(println)
  }

  def topN4(): Unit = {
    implicit val autoSorts = new Ordering[(Int, Int)] {
      override def compare(x: (Int, Int), y: (Int, Int)): Int = y._2.compareTo(x._2)
    }


    val kv = data.map(t4 => ((t4._1, t4._2), (t4._3, t4._4)))
    val res = kv.combineByKey(
      (v1: (Int, Int)) => {
        Array(v1, (0, 0), (0, 0))
      },

      (oldv: Array[(Int, Int)], newv: (Int, Int)) => {
        var flag = 0
        for (i <- 0 until oldv.length) {
          if (oldv(i)._1 == newv._1) {
            if (oldv(i)._2 < newv._2) {
              flag = 1
              oldv(i) = newv
            } else {
              flag = 2
            }
          }
        }

        //println(oldv.length + "--------------"+oldv(oldv.length-1)._1)
        if (flag == 0) {
          oldv(oldv.length - 1) = newv
        }

        oldv.sorted
        //scala.util.Sorting.quickSort(oldv)
        //oldv
      },
      (v1: Array[(Int, Int)], v2: Array[(Int, Int)]) => {
        val unioned = v1.union(v2)
        unioned.sorted
      }
    )

    res.map(x => (x._1, x._2.toList)).foreach(println)
  }

}
