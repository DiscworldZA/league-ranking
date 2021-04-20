package com.span.leagueranking.services

import com.span.leagueranking.errors.decoder.DecoderError
import com.span.leagueranking.models.{Game, Team}

import scala.util.matching.Regex

object Decoder {

  object Scoring {
    val gameMatch: Regex = "([A-Za-z ]+)([0-9])+, ([A-Za-z ]+)([0-9])+".r

    //As per requirement no validation is needed but did it anyway
    def stringToGame(string: String): Either[DecoderError, Game] = {
      string match {
        case gameMatch(team1Name: String, team1Score: String, team2Name: String, team2Score: String) =>
          val team1 = Team(team1Name.trim)
          val team2 = Team(team2Name.trim)
          Right(Game(team1, team1Score.toInt, team2, team2Score.toInt))
        case _ => Left(DecoderError(string))
      }
    }
  }

}
