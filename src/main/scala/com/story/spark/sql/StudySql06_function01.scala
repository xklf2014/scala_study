package com.story.spark.sql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, IntegerType, StructField, StructType}

object StudySql06_function01 {
  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder()
      .appName("function")
      .master("local")
      .getOrCreate()

    import session.implicits._

    var dataDF = List(
      ("A", 1, 90),
      ("B", 1, 90),
      ("C", 2, 60),
      ("D", 1, 70),
      ("E", 2, 40),
      ("F", 1, 30),
      ("G", 2, 100)
    ).toDF("name", "class", "score")
    dataDF.createTempView("users")
    //session.sql("select class,sum(score) sums from users group by class order by sums desc").show()

    //session.sql("select * from users order by name desc,score").show()

    //注册函数
    //session.udf.register("f1",(x:Int)=>{x*10})

    //session.sql("select * ,f1(score) from users").show()


    //session.udf.register("myavg", new MyAvgFun)
    //session.sql("select class,myavg(score) from users group by class").show()

    /*session.sql("select name," +
      "case " +
      " when score >= 90 then '优' " +
      " when score >= 80 then '良' " +
      " when score >= 60 then '及格' " +
      " else '差' end as grade" +
      " from users ").show()

    println("---------------------")

    session.sql("select count(*) from users group by " +
      "case " +
      " when score >= 90 then '优' " +
      " when score >= 80 then '良' " +
      " when score >= 60 then '及格' " +
      " else '差' end "
      ).show()*/

    /*   session.sql("select name," +
         " explode(split(concat(" +
         " case when class = 1 then 'one' else 'two' end,' ',score),' ')) " +
         " from users").show()*/

    //开窗函数
    /*    session.sql(
          "select * from (select class,score, " +
          " rank() over(partition by class order by score desc) rk" +
          " from users) rs where rs.rk <=3").show()*/

    //session.sql("select *,count(score) over(partition by class) as n from users").show()

    /*    val res = session.sql("" +
          "select t1.name,t1.class,t2.score " +
          "" +
          " from " +
          " (select name,class from users) as t1 " +
          " join " +
          " (select name,score from users) as t2 " +
          " on t1.name = t2.name " +
          " where t2.score > 60"
        )*/

    //score#34 + 100   优化成+100，减少一次cpu计算
    val res = session.sql("" +
      "select t1.name,t1.class,t2.score+20+80 " +
      "" +
      " from " +
      " (select name,class from users) as t1 " +
      " join " +
      " (select name,score from users) as t2 " +
      " on t1.name = t2.name " +
      " where t2.score > 60"
    )
    res.show()
    println("-----------------")
    res.explain(true)

  }

  class MyAvgFun extends UserDefinedAggregateFunction {
    //myavf(score)
    override def inputSchema: StructType = {
      StructType.apply(Array(StructField.apply("score", IntegerType, false)))
    }

    override def bufferSchema: StructType = {
      // avg = sum / count
      StructType.apply(
        Array(
          StructField.apply("sum", IntegerType, false),
          StructField.apply("count", IntegerType, false)
        )
      )
    }

    override def dataType: DataType = DoubleType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0
      buffer(1) = 1
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      //每调用一次，sum求和，count+1
      buffer(0) = buffer.getInt(0) + input.getInt(0)
      buffer(1) = buffer.getInt(1) + 1
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getInt(0) + buffer2.getInt(0)
      buffer1(1) = buffer1.getInt(1) + buffer2.getInt(1)
    }

    override def evaluate(buffer: Row): Double = {
      buffer.getInt(0) / buffer.getInt(1)
    }
  }

}
