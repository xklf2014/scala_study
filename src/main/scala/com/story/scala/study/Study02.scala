package com.story.scala.study

import java.util.Date

object Study02 {
  def main(args: Array[String]): Unit = {

    def fun01(): Unit = {
      println("hello world")
    }

    fun01()

    var x = 3
    var y = fun01()
    println(y) // -> ()

    def fun02(): Unit = {
      println("hello unit")
    }

    //有return需要给出返回类型
    def fun03(): Int = {
      return 100
    }

    //不用return可以自动推断
    def fun04() = {
      100
    }

    def fun05(a: Int): Unit = {
      println(a)
    }

    fun05(99)


    println("-----------递归函数-------------")

    def fun06(num: Int): Int = {
      if (num == 1) {
        num
      } else {
        num * fun06(num - 1)
      }
    }

    val rs = fun06(3)
    println(rs)


    def fun07(a: Int = 8, b: String = "hello"): Unit = {
      println(s"$a , $b")
    }

    fun07()
    fun07(22)
    fun07(22, "hi")
    fun07(b = "heihei")

    println("---------匿名函数----------")
    var f =(a: Int, b: Int) => {
      a + b
    }
    val result = f(10,20)
    println(result)

    //签名: (参数类型列表) => 返回值类型
    var f1:(Int,Int)=>Int =(a: Int, b: Int) => {
      a * b
    }


    println("-----------嵌套函数------------")

    def fun08(a:String): Unit ={
      def fun(): Unit ={
        println(a)
      }
      fun()
    }

    fun08("hahaha")

    def fun09(date:Date,tp:String,msg:String): Unit ={
      println(s"$date\t$tp\t$msg")
    }

    fun09(new Date(),"info","ok")

    var info = fun09(_:Date,"info","ok")
    var error = fun09(_:Date,"error",_:String)
    info(new Date())
    error(new Date(),"ng")

    //可变参数类型需要保持一致
    def fun10(a:Int*): Unit ={
      for(e <- a){
        println(e)
      }

      a.foreach((x:Int) => {
        println(x)
      })

      a.foreach(println(_))

    }

    fun10(1,2,3,4,5,6,7,8)

    def cal(a:Int,b:Int,f:(Int,Int)=>Int):Unit={
      val res :Int = f(a,b)
      println(res)
    }

    cal(3,10,(x:Int,y:Int)=>{x+y})
    cal(3,10,(x:Int,y:Int)=>{x*y})
    cal(3,10,(x:Int,y:Int)=>{x-y})
    cal(3,10,(x:Int,y:Int)=>{x/y})

    def factory(i:String):(Int,Int)=>Int ={
      def plus(x:Int,y:Int):Int = {
        x + y
      }

      def minus(x:Int,y:Int):Int = {
        x - y
      }

      if(i.equals("+")){
        plus
      }else if(i.equals("-")){
        minus
      }else{
        _*_
      }
    }

    cal(10,10,_*_)

    cal(5,10,factory("-"))

    def fun11(a:Int*)(b:String*): Unit ={
      a.foreach(println)
      b.foreach(println)
    }

    fun11(1,2,3,4)("a","b","c")

    //方法不想执行，赋值给一个引用 方法名 + 空格 + 下划线
    val func = fun01 _


  }


}
