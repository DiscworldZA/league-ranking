package com.span.leagueranking.errors

case class GenericUnhandledException(ex: Exception) extends ServiceError {
  override val message: String = "Generic unhandled Exception"
}
