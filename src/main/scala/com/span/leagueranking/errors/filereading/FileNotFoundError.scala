package com.span.leagueranking.errors.filereading

case class FileNotFoundError(filePath: String) extends FileReadError {
  override val message: String = "File was not found"
}
