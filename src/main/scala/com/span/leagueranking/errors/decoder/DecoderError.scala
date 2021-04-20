package com.span.leagueranking.errors.decoder

import com.span.leagueranking.errors.ServiceError

case class DecoderError(text: String) extends ServiceError {
  override val message: String = "Decoding Failed"
}
