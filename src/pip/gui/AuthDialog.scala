package pip.gui

import pip.core.{Loc, Tools}
import swing._
import event.ButtonClicked

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 6.3.11
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */

object AuthDialog {
  import Tools.waitUntil

  /**
   * Metoda vraci PIN jako String, anebo ""
   */
  def pin = {
    //vytvorit instanci dialogu, zobrazit ho
    val dialog = new MainFrame {
      var result: Option[String] = None

      object Ok extends Button {
        text = Loc("ok")
      }

      object Close extends Button {
        text = Loc("close")
      }

      val pin = new TextField

      contents = new BoxPanel(Orientation.Horizontal) {
        contents += Ok
        contents += Close
        contents += pin
      }

      listenTo(Ok,Close)

      reactions += {
        case ButtonClicked(Ok) =>
          result = Some(pin.text)
          close
        case ButtonClicked(Close) =>
          close
      }

      override def closeOperation {
        dispose
      }
    }
    //zobrazit ho
    dialog.visible = true
    //pockat dokud je visible
    waitUntil(dialog.visible)
    //vratit hodnotu result, anebo ""
    dialog.result.getOrElse("")
  }
}