package leagueranking

import com.span.leagueranking.errors.decoder.DecoderError
import com.span.leagueranking.models.{Game, Team}
import com.span.leagueranking.services.Decoder

class DecoderSpec extends UnitSpec {

  "ScoringDecoder" should "decode game" in {
    val line = "Lions 3, Snakes 3"
    val team1 = Team("Lions")
    val team1Score = 3
    val team2 = Team("Snakes")
    val team2Score = 3
    val game = Game(team1, team1Score, team2, team2Score)

    Decoder.Scoring.stringToGame(line).right.value should be(game)
  }

  it should "decode multi spaced names" in {
    val line = "Tarantulas 1, FC Awesome 0"
    val team1 = Team("Tarantulas")
    val team1Score = 1
    val team2 = Team("FC Awesome")
    val team2Score = 0
    val game = Game(team1, team1Score, team2, team2Score)

    Decoder.Scoring.stringToGame(line).right.value should be(game)
  }

  it should "be decoding error on invalid game" in {
    val line = "Not A valid Game"

    Decoder.Scoring.stringToGame(line).left.value shouldBe a[DecoderError]
  }

  //TODO add invalid input handling tests
}
