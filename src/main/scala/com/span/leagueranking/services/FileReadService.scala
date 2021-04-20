package com.span.leagueranking.services

import com.span.leagueranking.errors.filereading.FileNotFoundError
import com.span.leagueranking.errors.{GenericUnhandledException, ServiceError}
import com.span.leagueranking.util.Control.using

import java.io.FileNotFoundException
import scala.io.Source

object FileReadService {

  def handleException[T](filePath: String): PartialFunction[Throwable, Left[ServiceError, Nothing]] = {
    case _: FileNotFoundException => Left(FileNotFoundError(filePath))
    case e: Exception => Left(GenericUnhandledException(e))
  }

  def processLines[T](filePath: String)(f: String => T): Either[ServiceError, List[T]] = {
    try {
      Right(using(Source.fromFile(filePath)) { source =>
        (for {
          line <- source.getLines()
        } yield f(line)).toList
      })
    } catch handleException(filePath)
  }

  def readLines[T](filePath: String): Either[ServiceError, List[String]] = {
    try {
      Right(using(Source.fromFile(filePath)) { source =>
        (for {
          line <- source.getLines()
        } yield line).toList
      })
    } catch handleException(filePath)
  }
}
