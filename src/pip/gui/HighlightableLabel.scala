package pip.gui

import javax.swing.Icon
import swing.Label
import java.awt.Font
import pip.core.Implicits

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 19.3.11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */

trait HighlightableLabel extends Label {
  import Implicits.encloseStringInHtmlTag

  val defaultIcon: Icon
  val highLightIcon: Icon
  val defaultText: String

  def highLight() {
    text = defaultText.tagU.tagHtml
    icon = highLightIcon
  }

  def deHighlight() {
    text = defaultText
    icon = defaultIcon
  }
}