package com.story.spark.sql



import java.io.FileInputStream
import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object StudySql03_jdbc {
  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder()
      .config("","")
      //.config("spark.sql.shuffle.partitions","1")
      .appName("test")
      .master("local")
      .getOrCreate()

    val sc = session.sparkContext
    sc.setLogLevel("INFO")

    val properties = new Properties()
    val path = Thread.currentThread().getContextClassLoader.getResource("jdbc.properties").getPath
    properties.load(new FileInputStream(path))

/*    val p = new Properties()
    p.put("url",properties.getProperty("url"))
    p.put("user",properties.getProperty("user"))
    p.put("password",properties.getProperty("password"))
    p.put("driver","com.mysql.jdbc.Driver")*/


    /*val jdbcDF = session.read.jdbc(p.get("url").toString,"emp",p)
    jdbcDF.createTempView("emp")
    session.sql("select * from emp").show()

    jdbcDF.write.jdbc(p.get("url").toString,"new",p)*/


   /* val userDF = session.read.jdbc(p.get("url").toString,"users",p)
    val scoreDF = session.read.jdbc(p.get("url").toString,"score",p)

    userDF.createTempView("users")
    scoreDF.createTempView("score")*/

    //val rs = session.sql("select u.id,u.name,u.age,s.score from users u join score s on u.id = s.id")
    //println(rs.rdd.partitions.length)
    //val resDF = rs.coalesce(1)
    //resDF.write.jdbc(p.get("url").toString,"combine2",p)

    val uDF = session.read.jdbc(properties.get("url").toString,"users",properties)
    uDF.createTempView("users")
    session.sql("select * from users").show()

  }

}
