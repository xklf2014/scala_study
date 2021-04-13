package com.story.spark.sql

import org.apache.spark.sql.SparkSession

object StudySql04_hive {
  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder()
      .config("spark.sql.shuffle.partitions","1")
      .appName("test")
      .master("local")
      .enableHiveSupport()
      .config("spark.sql.warehouse.dir","E:/spark/warehouse")
      .getOrCreate()

    val sc = session.sparkContext
    sc.setLogLevel("ERROR")

    //session.sql("create table test(name string,age int)")
    //session.catalog.listTables().show()

    import session.sql
    //sql("create database story")
    sql("use story")
    //sql("create table test1(name string,age int)")
    sql("insert into test1 values('zhangsan',18),('lisi',20)")
    sql("select * from test1").show()
  }

}
