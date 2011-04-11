package pip.gui

import scala.swing.{Graphics2D, MenuBar}
class URLMenu extends MenuBar {
  override def paintComponent(g: Graphics2D) {
    val original = g.getPaint
    g.setColor(Colors.transparent)
    g.fillRect(0, 0, size.width, size.height)
  }
}
