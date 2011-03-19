package pip.gui

import scala.swing._
import scala.swing.event._
import java.awt.Color

class URLMenu extends MenuBar {
  override def paintComponent(g: Graphics2D) {
    val original = g.getPaint
    g.setColor(Color.white)
    g.fillRect(0, 0, size.width, size.height)
  }
}
