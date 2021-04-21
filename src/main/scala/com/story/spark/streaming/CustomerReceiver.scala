package com.story.spark.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver


class CustomerReceiver(host: String, port: Int) extends Receiver[String](StorageLevel.DISK_ONLY) {
  override def onStart(): Unit = {
    new Thread {
      override def run(): Unit = {
        acc()
      }
    }.start()
  }

  private def acc(): Unit ={
    val server = new Socket(host,port)
    val reader = new BufferedReader(new InputStreamReader(server.getInputStream))
    var line = reader.readLine()
    while(!isStopped() && line != null){
      store(line)
      line = reader.readLine()
    }
  }

  override def onStop(): Unit = ???
}
