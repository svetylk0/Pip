package pip.gui

import swing._
import event.Key
import event._
import java.awt.event.KeyListener
import javax.swing.{JComponent, ImageIcon}


/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 23.3.11
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */

class ImageView(image: ImageIcon, parent: UIElement = null) extends MainFrame
      with DisposeOnClose {

  object Img extends Label {
      icon = image
    }

  val panel = new BoxPanel(Orientation.Horizontal) {
    contents += Img
  }

  contents = panel

  listenTo(Img.mouse.clicks, panel.keys)

  reactions += {
     case MouseClicked(_,_,_,_,_) =>
       dispose
     case KeyPressed(_, key,_,_) =>
       if (key == Key.Escape) dispose
  }

  if (parent == null) peer.setLocationRelativeTo(null) else setLocationRelativeTo(parent)
  visible = true
  panel.requestFocus
}
