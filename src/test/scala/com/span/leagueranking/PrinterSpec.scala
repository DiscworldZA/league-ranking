package com.span.leagueranking

import com.span.leagueranking.models.{Points, Team}
import com.span.leagueranking.services.Printer

class PrinterSpec extends UnitSpec {
  "Printer" must "build result text" in {
    val team1 = Team("Tarantulas")
    val points1 = Points(team1, 3)
    val team2 = Team("FC Awesome")
    val points2 = Points(team2, 2)
    val team3 = Team("Grouches")
    val points3 = Points(team3, 0)
    val result =
      s"1. Tarantulas, 3 pts${System.lineSeparator()}" +
        s"2. FC Awesome, 2 pts${System.lineSeparator()}" +
        s"3. Grouches, 0 pts"
    Printer.buildResultText(List(points1, points2, points3)) should be(result)
  }
  it must "build result text with 1 pt" in {
    val team1 = Team("Tarantulas")
    val points1 = Points(team1, 2)
    val team2 = Team("FC Awesome")
    val points2 = Points(team2, 1)
    val result =
      s"1. Tarantulas, 2 pts${System.lineSeparator()}" +
        s"2. FC Awesome, 1 pt"
    Printer.buildResultText(List(points1, points2)) should be(result)
  }

}
