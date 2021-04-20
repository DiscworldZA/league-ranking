package leagueranking

import com.span.leagueranking.models.{Game, Points, Team}
import com.span.leagueranking.services.ScoringService

class ScoringServiceSpec extends UnitSpec {
  val winPoint = 3
  val tiePoint = 1
  val losePoint = 0

  "ScoringService" should "determine team1 win points" in {
    val team1 = Team("Tarantulas")
    val team1Score = 1
    val team2 = Team("FC Awesome")
    val team2Score = 0
    val game = Game(team1, team1Score, team2, team2Score)
    val result = List(Points(team1, winPoint), Points(team2, losePoint))
    ScoringService.determinePoints(game) should contain only (result: _*)
  }

  it should "determine team2 win points" in {
    val team1 = Team("Tarantulas")
    val team1Score = 0
    val team2 = Team("FC Awesome")
    val team2Score = 1
    val game = Game(team1, team1Score, team2, team2Score)
    val result = List(Points(team1, losePoint), Points(team2, winPoint))
    ScoringService.determinePoints(game) should contain only (result: _*)
  }

  it should "determine tie points" in {
    val team1 = Team("Tarantulas")
    val team1Score = 1
    val team2 = Team("FC Awesome")
    val team2Score = 1
    val game = Game(team1, team1Score, team2, team2Score)

    val result = List(Points(team1, tiePoint), Points(team2, tiePoint))
    ScoringService.determinePoints(game) should be(result)
  }

  it should "sum points" in {
    val team1 = Team("Tarantulas")
    val points1 = Points(team1, 3)
    val points2 = Points(team1, 1)
    val team2 = Team("FC Awesome")
    val points3 = Points(team2, 1)
    val points4 = Points(team2, 1)
    val result = List(Points(team1, 4), Points(team2, 2))
    ScoringService.sumPoints(List(points1, points2, points3, points4)) should contain only (result: _*)
  }

  it should "sum 0 points" in {
    val team1 = Team("Tarantulas")
    val points1 = Points(team1, 3)
    val points2 = Points(team1, 1)
    val team2 = Team("FC Awesome")
    val points3 = Points(team2, 0)
    val points4 = Points(team2, 0)
    val result = List(Points(team1, 4), Points(team2, 0))
    ScoringService.sumPoints(List(points1, points2, points3, points4)) should contain only (result: _*)
  }

  it should "sort points" in {
    val team1 = Team("Tarantulas")
    val points1 = Points(team1, 1)
    val team2 = Team("FC Awesome")
    val points2 = Points(team2, 4)
    val team3 = Team("Snakes")
    val points3 = Points(team3, 2)
    val team4 = Team("Grouches")
    val points4 = Points(team4, 0)
    val result = List(points2, points3, points1, points4)
    ScoringService.sortPoints(List(points1, points2, points3, points4)) should be(result)
  }

  it should "sort equal points alphabetically" in {
    val team1 = Team("Tarantulas")
    val points1 = Points(team1, 1)
    val team2 = Team("FC Awesome")
    val points2 = Points(team2, 1)

    val team3 = Team("Snakes")
    val points3 = Points(team3, 3)
    val team4 = Team("Grouches")
    val points4 = Points(team4, 3)
    val result = List(points4, points3, points2, points1)
    ScoringService.sortPoints(List(points1, points2, points3, points4)) should be(result)
  }

}
