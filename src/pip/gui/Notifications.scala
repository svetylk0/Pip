package pip.gui

import javax.swing.border.{EmptyBorder, LineBorder}
import swing._
import java.awt.Toolkit
import pip.core.Tools

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 21.3.11
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */

object Notifications {
  import Tools.thread

  def simpleNotification(msg: String,
                         parent: UIElement = null,
                         autoVisible: Boolean = true) = new MainFrame with DisposeOnClose {
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

    visible = autoVisible
  }

  private val screenSize = Toolkit.getDefaultToolkit().getScreenSize()
  private val (screenWidth, screenHeight) = (screenSize.getWidth.toInt, screenSize.getHeight.toInt)

  def animatedRightDownCornerSimpleNotification(text: String, timeout: Long = 3000) = {
    val notif = simpleNotification(msg = text, autoVisible = false)
    val dim = notif.size
    val (w,h) = (dim.getWidth.toInt, dim.getHeight.toInt)

    notif.location = new Point(screenWidth-w,screenHeight)

    val x = screenWidth-w

    //nabeh
    thread {
      notif.visible = true
      for(y <- Range((screenHeight-h),screenHeight).reverse) {
        notif.location = new Point(x,y)
        Thread.sleep(10l)
      }
    }

    //cekani a konec
    thread {
      Thread.sleep(timeout)
      for(y <- Range((screenHeight-h),screenHeight)) {
        notif.location = new Point(x,y)
        Thread.sleep(10l)
      }
      notif.dispose
    }
  }
}