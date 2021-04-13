package com.story.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrame, Dataset, Encoders, RelationalGroupedDataset, Row, SaveMode, SparkSession}

object StudySql02_basic {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val session = SparkSession.builder().config(conf).getOrCreate()

    val sc = session.sparkContext
    sc.setLogLevel("ERROR")

    import session.implicits._

    val dataDF:DataFrame = List(
      "hello world",
      "hello spark",
      "hello world",
      "hello spark",
      "hello world",
      "hello scala",
      "hello world"
    ).toDF("line")

    dataDF.createTempView("worldcount")

    val df = session.sql("select * from worldcount")
    df.show()
    df.printSchema()

    println("---------------------------")

    session.sql(" select word,count(*) cnt from  (select explode(split(line,' ')) as word from worldcount) t group by t.word order by cnt desc").show()

    println("---------------------------")

    val subTab = dataDF.selectExpr("explode(split(line,' ')) as word")
    val dataset = subTab.groupBy("word")
    val rs = dataset.count()
    rs.show()

    rs.write.mode(SaveMode.Append).parquet("./data/out/wordcount")
    val frame = session.read.parquet("./data/out/wordcount")
    frame.show()
    frame.printSchema()
  }
}
