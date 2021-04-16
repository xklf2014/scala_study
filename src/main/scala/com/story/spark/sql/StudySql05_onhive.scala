package com.story.spark.sql

import org.apache.spark.sql.SparkSession

object StudySql05_onhive {
  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder()
      .config("spark.sql.shuffle.partitions","1")
      .appName("test")
      .master("local")
      .enableHiveSupport()
      .config("spark.metastore.uris","thrift://zknode02:9083")
      .getOrCreate()

    val sc = session.sparkContext
    sc.setLogLevel("ERROR")

    import  session.implicits._

    val df01 = List(
      "zhangsan",
      "lisi"
    ).toDF
    df01.createTempView("tmp")

    //session.sql("create table tmp1(id int)")
    session.sql("insert into tmp1 values (3),(4),(5)")
    df01.write.saveAsTable("tmp2")

    session.catalog.listTables().show()
    val df = session.sql("show tables")
    df.show()



  }

}
