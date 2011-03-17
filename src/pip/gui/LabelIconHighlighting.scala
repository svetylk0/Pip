package pip.gui

import swing.Label
import javax.swing.Icon

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 17.3.11
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */

trait LabelIconHighlighting extends Label {
  val defaultIcon: Icon
  val highLightIcon: Icon

  def highLight() {
    icon = highLightIcon
  }

  def deHighlight() {
    icon = defaultIcon
  }
}