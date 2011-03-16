package pip.gui

import swing._
import event.ButtonClicked
import pip.core._

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 6.3.11
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */

class AuthDialog(authUrl: String) extends MainFrame with DisposeOnClose {

  import Tools.waitUntil
  import Globals._
  import Colors._
  import Implicits.encloseStringInHtmlTag

  var result: Option[String] = None

  object Ok extends Button {
    text = Loc("ok")
  }

  object Close extends Button {
    text = Loc("close")
  }

  val message = new FlowPanel {
    contents += new Label {
      text = Loc("pinMessage").tagHtml
    }
  }

  object Url extends FlowPanel {
    contents += new TextArea {
      editable = false
      background = backgroundColor
      foreground = blue
      text = authUrl
    }
  }

  val pinTextArea = new TextField(6)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += message
    contents += Url
    contents += new FlowPanel {
      contents += new Label {
        text = Loc("pin")
      }
      contents += pinTextArea
    }
    contents += new FlowPanel(Ok, Close)
  }

  listenTo(Ok, Close)

  reactions += {
    case ButtonClicked(Ok) =>
      result = Some(pinTextArea.text)
      close
    case ButtonClicked(Close) =>
      close
  }


  /**
   * Metoda vraci PIN jako String, anebo ""
   */
  def getPin = {
    //zobrazit
    visible = true
    //pockat dokud je visible
    waitUntil(visible)
    //vratit hodnotu result, anebo ""
    result.getOrElse("")
  }

}

/*
object AuthDialog {
  //test
  def main(a: Array[String]) {
    Loc.load("czech.loc")
    println("PIN: " + new AuthDialog(Auth.authURL).getPin)
    exit
  }

} */