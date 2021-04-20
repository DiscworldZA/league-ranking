package leagueranking

import com.span.leagueranking.errors.filereading.FileNotFoundError
import com.span.leagueranking.services.FileReadService

import java.io.File

class FileServiceSpec extends UnitSpec {
  val dir = s".${File.separator}testresources"

  "The File Service" should "read file" in {
    val testTxt = List("test")
    FileReadService.readLines(s"$dir${File.separator}read.txt").right.value should be(testTxt)
  }

  it should "return FileNotFound on non-existing files" in {
    FileReadService.readLines(s"$dir${File.separator}doesNotExist.txt").left.value shouldBe a[FileNotFoundError]
  }

  it should "process lines" in {
    val result = List("Thisa", "isa", "aa", "linea", "testa")
    FileReadService.processLines(s"$dir${File.separator}lines.txt") { line =>
      line + "a"
    }.right.value should be(result)
  }
}
