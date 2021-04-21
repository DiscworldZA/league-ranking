package com.span.leagueranking.services

import com.span.leagueranking.errors.ServiceError
import com.span.leagueranking.errors.decoder.DecoderError
import com.span.leagueranking.errors.scoring.InvalidGameTextError
import com.span.leagueranking.models.{Game, Points, Result}

object ScoringService {
  def scoreFile(filePath: String): Either[ServiceError, List[Result]] = {
    FileReadService.processLines(filePath) { line =>
      Decoder.Scoring.stringToGame(line)
    } match {
      case Left(value) => Left(value)
      case Right(value) => processDecoding(value)
    }
  }

  //TODO Fail this on file read instead of after
  def processDecoding: List[Either[DecoderError, Game]] => Either[ServiceError, List[Result]] = { decodingResult =>
    /*
    This is argumentatively slower BUT reads better
    decodingResult.partition(_.isLeft) match {
      case (Nil, validGames) =>
        val games = (for (Right(game) <- validGames) yield determinePoints(game)).flatten
        Right(sumPoints andThen sortPoints andThen determinePositions apply games)
      case (decodingErrors, _) =>
        val errors = for (Left(error) <- decodingErrors) yield error
        Left(InvalidGameTextError(errors))
    }*/

    //This is a faster implementation since it only traverses twice if needed
    val invalidGames = decodingResult.collect { case Left(error) => error }
    if (invalidGames.nonEmpty) {
      Left(InvalidGameTextError(invalidGames))
    } else {
      val validGames = decodingResult.collect { case Right(game) => game }
      //This is now functionally composed, how pretty
      Right(sumPoints andThen sortPoints andThen determinePositions apply validGames.flatMap(determinePoints))
    }
  }

  def determinePoints: Game => List[Points] = { game =>
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

  def sumPoints: List[Points] => List[Points] = { points =>
    points.foldRight(List[Points]())((point, list) => {
      list.find(points => points.team == point.team) match {
        case Some(x) => Points(x.team, x.points + point.points) +: list.filter(points => points.team != point.team)
        case None => point +: list
      }
    })
  }

  def sortPoints: List[Points] => List[Points] = { points =>
    points.sortBy(point => (point.points, point.team.name))(Ordering.Tuple2(Ordering.Int.reverse, Ordering.String))
  }

  //Does not break order of points passed in
  def determinePositions: List[Points] => List[Result] = { points =>
    points.foldLeft(List[Result]())((list, point) => {
      list.lastOption match {
        case Some(value) if value.points == point.points => list :+ Result(point.team, point.points, value.position)
        case Some(value) => list :+ Result(point.team, point.points, value.position + 1)
        case None => list :+ Result(point.team, point.points, 1)
      }
    })
  }
}
