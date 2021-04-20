package com.span.leagueranking.services

import com.span.leagueranking.models.Points

object Printer {
  def buildResultText(points: List[Points]): String = {
    (for {
      //TODO Inefficient on large arrays since this traverses twice, move indexing to sorting
      (point, index) <- points.zipWithIndex
    } yield {
      //Haha 1 point must be pt not pts
      s"${index + 1}. ${point.team.name}, ${point.points} ${if (point.points == 1) "pt" else "pts"}"
    }).mkString(System.lineSeparator())
  }
}
