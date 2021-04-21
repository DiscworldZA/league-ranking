package com.span.leagueranking.services

import com.span.leagueranking.models.Result

object Printer {
  def buildResultText(results: List[Result]): String = {
    (for {
      result <- results
    } yield {
      //Haha 1 point must be pt not pts
      s"${result.position}. ${result.team.name}, ${result.points} ${if (result.points == 1) "pt" else "pts"}"
    }).mkString(System.lineSeparator())
  }
}
