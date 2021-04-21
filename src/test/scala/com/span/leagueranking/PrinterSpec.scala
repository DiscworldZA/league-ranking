package com.span.leagueranking

import com.span.leagueranking.models.{Result, Team}
import com.span.leagueranking.services.Printer

class PrinterSpec extends UnitSpec {
  "Printer" must "build result text" in {
    val team1 = Team("Tarantulas")
    val result1 = Result(team1, 3, 1)
    val team2 = Team("FC Awesome")
    val result2 = Result(team2, 2, 2)
    val team3 = Team("Grouches")
    val result3 = Result(team3, 0, 3)
    val result =
      s"1. Tarantulas, 3 pts${System.lineSeparator()}" +
        s"2. FC Awesome, 2 pts${System.lineSeparator()}" +
        s"3. Grouches, 0 pts"
    Printer.buildResultText(List(result1, result2, result3)) should be(result)
  }
  it must "build result text with 1 pt" in {
    val team1 = Team("Tarantulas")
    val result1 = Result(team1, 2, 1)
    val team2 = Team("FC Awesome")
    val result2 = Result(team2, 1, 2)
    val result =
      s"1. Tarantulas, 2 pts${System.lineSeparator()}" +
        s"2. FC Awesome, 1 pt"
    Printer.buildResultText(List(result1, result2)) should be(result)
  }


  it must "build must print tied teams the same position" in {
    val team1 = Team("Tarantulas")
    val result1 = Result(team1, 4, 1)
    val team2 = Team("FC Awesome")
    val result2 = Result(team2, 2, 2)
    val team3 = Team("Tarantulas")
    val result3 = Result(team3, 2, 2)
    val team4 = Team("Lions")
    val result4 = Result(team4, 1, 3)
    val result =
      s"1. Tarantulas, 4 pts${System.lineSeparator()}" +
        s"2. FC Awesome, 2 pts${System.lineSeparator()}" +
        s"2. Tarantulas, 2 pts${System.lineSeparator()}" +
        s"3. Lions, 1 pt"
    Printer.buildResultText(List(result1, result2, result3, result4)) should be(result)
  }
}
