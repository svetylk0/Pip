package pip.gui

import javax.swing.border.{EmptyBorder, LineBorder}
import swing._

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 21.3.11
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */

object Notifications {
  def simpleNotification(msg: String, parent: UIElement = null) = new MainFrame with DisposeOnClose {
    peer.setUndecorated(true)

    val label = new Label {
      text = msg
      border = new EmptyBorder(10,10,10,10)
    }

    def setText(s: String) {
      label.text = s
      repaint
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += label
      border = new LineBorder(Colors.black,1)
    }

    if (parent == null) peer.setLocationRelativeTo(null) else setLocationRelativeTo(parent)

    visible = true
  }
}