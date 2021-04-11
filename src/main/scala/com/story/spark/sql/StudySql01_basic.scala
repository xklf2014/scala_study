package com.story.spark.sql

import org.apache.avro.io.Encoder
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataType, DataTypes, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Dataset, Encoders, RelationalGroupedDataset, Row, SaveMode, SparkSession}

import scala.beans.BeanProperty

class Person{
  @BeanProperty
  var name :String = ""
  @BeanProperty
  var age : Int = 0
}

object StudySql01_basic {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val session = SparkSession.builder().config(conf).getOrCreate()

    val sc = session.sparkContext
    sc.setLogLevel("ERROR")

    //f1(session)
    //f2(session,sc)
    //f3(session,sc)
    //f4(session,sc)

    val ds01 = session.read.textFile("E:\\java_study\\scala_study\\data\\person.txt")
    val person = ds01.map(line => {
      val strs = line.split(" ")
      (strs(0),strs(1).toInt)
    })(Encoders.tuple(Encoders.STRING,Encoders.scalaInt))

    //import session.implicits._
   /* val ds01 = session.read.textFile("E:\\java_study\\scala_study\\data\\person.txt")
    val person = ds01.map(line => {
      val strs = line.split(" ")
      (strs(0),strs(1).toInt)
    })*/

    val cperson = person.toDF("name","age")
    cperson.show()
    cperson.printSchema()

  }



  def f1(session: SparkSession): Unit = {
    val dbs = session.catalog.listDatabases()
    dbs.show()

    val tbs = session.catalog.listTables()
    tbs.show()

    val fs = session.catalog.listFunctions()
    fs.show(999, true)

    println("------------------")

    val df = session.read.json("data/data_json.txt")
    df.show()
    df.printSchema()

    df.createTempView("tmp")
    val frame = session.sql("select name from tmp")
    frame.show()
    println("------------------")

    session.catalog.listTables().show()
  }

  def f2(session: SparkSession,sc: SparkContext): Unit ={
    val data = sc.textFile("E:\\java_study\\scala_study\\data\\person.txt")
    val rowRdd = data.map(_.split(" ")).map(arr => Row.apply(arr(0),arr(1).toInt))

    val fields = Array(
      StructField.apply("name",DataTypes.StringType,true),
      StructField.apply("age",DataTypes.IntegerType,true)
    )
    val schema = StructType.apply(fields)
    val dataframe = session.createDataFrame(rowRdd,schema)
    dataframe.show()

    dataframe.printSchema()
    dataframe.createTempView("person")
    session.sql("select * from person").show()
  }

  def f3(session:SparkSession,sc:SparkContext): Unit ={
    val data = sc.textFile("E:\\java_study\\scala_study\\data\\person.txt")
    val userSchema = Array(
      "name string",
      "age int",
      "gender int"
    )
    def toDtaType(rs:(String,Int)) ={
      userSchema(rs._2).split(" ")(1) match {
        case "string" => rs._1.toString
        case "int" => rs._1.toInt
      }
    }

    val rowRdd = data.map(_.split(" "))
      .map(x => x.zipWithIndex)
      .map(x => x.map(toDtaType(_)))
      .map(x => Row.fromSeq(x))

    def getDataType(v : String) ={
      v match {
        case "string" => DataTypes.StringType
        case "int" => DataTypes.IntegerType
      }
    }

    val fields = userSchema.map(_.split(" ")).map(x => StructField.apply(x(0),getDataType(x(1))))
    val schema = StructType.apply(fields)
    //val schema = StructType.fromDDL("name string,age int,gender int")
    val df = session.createDataFrame(rowRdd,schema)
    //val df = session.createDataFrame(rowRdd,schema)
    df.show()
    df.printSchema()
  }

  def f4(session:SparkSession,sc:SparkContext): Unit ={
    val data = sc.textFile("E:\\java_study\\scala_study\\data\\person.txt")
    val rddPerson = data.map(_.split(" ")).map(
      arr => {
        val p = new Person
        p.setName(arr(0))
        p.setAge(arr(1).toInt)
        p
      }
    )

    val df = session.createDataFrame(rddPerson,classOf[Person])
    df.show()
    df.printSchema()
  }

}
