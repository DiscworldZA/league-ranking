package com.span.leagueranking.errors.scoring

import com.span.leagueranking.errors.ServiceError
import com.span.leagueranking.errors.decoder.DecoderError

case class InvalidGameTextError(decodingErrors: List[DecoderError]) extends ServiceError {
  override val message: String = "The game file has invalid text"
}