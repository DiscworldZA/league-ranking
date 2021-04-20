package com.span.leagueranking.services

import com.span.leagueranking.errors.ServiceError
import com.span.leagueranking.models.{Game, Points}

object ScoringService {
  def scoreFile(filePath: String): Either[ServiceError, List[Points]] = {
    FileReadService.processLines(filePath) { line =>
      Decoder.Scoring.stringToGame(line)
    } match {
      case Left(value) => Left(value)
      case Right(value) =>
        //Collect invalid games and report
        val invalidGames = value.collect { case Left(value) => value }
        invalidGames.foreach(decodeError => println(s"Decoding Error for ${decodeError.text}"))
        //Continue with Valid Games
        val validGames = value.collect { case Right(value) => value }
        val gamePoints = validGames.flatMap(determinePoints)
        val totalPoints = sumPoints(gamePoints)
        Right(sortPoints(totalPoints))
    }
  }

  def determinePoints(game: Game): List[Points] = {
    //TODO Find some way to config this
    val tiePoint = 1
    val winPoint = 3
    val losePoint = 0
    game match {
      case x if x.team2Score > x.team1Score => List(Points(x.team2, winPoint), Points(x.team1, losePoint))
      case x if x.team1Score > x.team2Score => List(Points(x.team1, winPoint), Points(x.team2, losePoint))
      case x => List(Points(x.team1, tiePoint), Points(x.team2, tiePoint))
    }
  }

  def sumPoints(points: List[Points]): List[Points] = {
    points.foldLeft(List[Points]())((a, b) => {
      a.find(points => points.team == b.team) match {
        case Some(x) => Points(x.team, x.points + b.points) +: a.filter(points => points.team != b.team)
        case None => b +: a
      }
    })
  }

  def sortPoints(point: List[Points]): List[Points] = {
    point.sortBy(point => (point.points, point.team.name))(Ordering.Tuple2(Ordering.Int.reverse, Ordering.String))
  }

}
