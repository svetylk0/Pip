package pip.gui

import io.Source
import javax.swing.{ImageIcon, Icon}
import java.net.URL
import util.matching.Regex
import pip.net.Http

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 23.3.11
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */

trait ImageService {
  val reg: Regex
  val url: String

  val http = new Http("","UTF-8")

  def getIcon() = {
    val data = http.Get(url)
    reg findFirstMatchIn data match {
      case Some(x) => new ImageIcon(new URL(x group 1))
      case None => new ImageIcon
    }
  }
}

class TwitpicService(val url: String) extends ImageService {
  val reg = """id="photo-display".+?src=\"(.+?)"""".r
}

class YfrogService(val url: String) extends ImageService {
  val reg = """full_image_click.+?src="(.+?)"""".r
}

object NoServiceAvailable extends ImageService {
  val reg = "".r
  val url = ""
}