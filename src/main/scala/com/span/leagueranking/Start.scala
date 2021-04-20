package com.span.leagueranking

import com.span.leagueranking.services.{Printer, ScoringService}
import com.span.leagueranking.util.FileUtils

import java.io.File

object Start extends App {
  val dir = s".${File.separator}scores"

  val files = FileUtils.getListOfFiles(dir)
  (for {
    file <- files
  } yield ScoringService.scoreFile(file.getAbsolutePath)).foreach {
    case Left(error) => println(error)
    case Right(value) => println(Printer.buildResultText(value))
  }
}
