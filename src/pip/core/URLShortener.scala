package pip.core

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 11.3.11
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */

trait URLShortener {
  import io.Source
  import util.matching.Regex.Match

  val urlReg = """\w+:\/\/[\S]+""".r

  val replacer = (m: Match) => try {
    Source.fromURL("http://jdem.cz/get?url=" + m.toString).mkString match {
      case "http://jdem.cz/fuck" => m.toString
      case url => url
    }
  } catch {
    case _ => m.toString
  }

  def replaceURLs(text: String) = {
    urlReg.replaceAllIn(text, replacer)
  }

}