package com.story.spark.output


import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}


object TestAntlr {
  def main(args: Array[String]): Unit = {

    val lexer = new mygrammarLexer(new ANTLRInputStream("{1,{2,3},4}"))
    val tokens = new CommonTokenStream(lexer)
    val parser = new mygrammarParser(tokens)
    val paserTree = parser.myinit()
    println(paserTree.toString(parser))

  }
}
