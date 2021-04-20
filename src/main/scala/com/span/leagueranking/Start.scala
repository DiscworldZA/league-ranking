package com.span.leagueranking

import com.span.leagueranking.services.{Printer, ScoringService}
import com.span.leagueranking.util.FileUtils

import java.io.File

object Start extends App {
  val dir = s".${File.separator}scores"

  val files = FileUtils.getListOfFiles(dir)
  files.foreach { file =>
    ScoringService.scoreFile(file.getAbsolutePath) match {
      case Left(error) => println(error)
      case Right(value) =>
        println(s"Scores for ${file.getName}")
        println(Printer.buildResultText(value))
        println()
    }
  }
}
