package com.story.scala.study

object Study01 {
  def main(args: Array[String]): Unit = {
    var a1 = 3
    if(a1 > 1){
      println(s"a1 > 1")
    }else{
      println(s"a1 <= 1")
    }


    var a2 = 10
    while(a2 > 0) {
      println(s"a2 = ${a2}")
      a2 -= 1

    }

    println("----------------------")

    val seqs = 1 to 10
    println(seqs)

    val seqs1 = 1 to (10,2)
    println(seqs1)

    val seqs2 = 1 until 10
    println(seqs2)

    for (elem <- seqs) {
      print(elem+"\t")
    }

    println()
    for (elem <- seqs if(elem % 2 ==0)) {
      print(elem+"\t")
    }
  println()

    for(i <- 1 to 9){
      for(j <- 1 to 9 if(j <= i)){
        print(s"${i} * ${j} = ${i*j}\t")
      }
      println()
    }
    println()
    for(i <- 1 to 9;j <- 1 to 9 if(j <= i)){
        print(s"${i} * ${j} = ${i*j}\t")
        if(i == j) println()
      }

    val is = for( i <- 1 to 10 ) yield i
    println(is)

    val is1 = for( i <- 1 to 10 ) yield {
      var x = 10
      i + x

    }
    println(is1)
  }

}
