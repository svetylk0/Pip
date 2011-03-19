package pip.gui

import javax.swing.Icon
import swing.Label

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 19.3.11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */

trait HighlightableLabel extends Label {
  val defaultIcon: Icon
  val highLightIcon: Icon

  def highLight() {
    icon = highLightIcon
  }

  def deHighlight() {
    icon = defaultIcon
  }
}