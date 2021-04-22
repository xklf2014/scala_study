package com.story.spark.streaming

import java.io.PrintStream
import java.net.ServerSocket

object ProduceData {
  def main(args: Array[String]): Unit = {
    val listen = new ServerSocket(8889)
    println("server start")
    while (true){
      val client = listen.accept()
      println("client accept ----- 1")
      new Thread(){
        override def run(): Unit = {
          var num = 0
          if(client.isConnected){
            val out = client.getOutputStream
            val printer = new PrintStream(out)

            while(client.isConnected){
              num += 1
              printer.println(s"hello ${num}")
              printer.println(s"hi ${num}")
              printer.println(s"hi ${num}")
              Thread.sleep(1000)
            }
          }
        }
      }.start()
    }
  }
}
