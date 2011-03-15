package pip.gui

import pip.core.Loc
import scala.swing._
import scala.swing.event._

object Toolbar extends BoxPanel(Orientation.Horizontal) {
  val addTweet = new Button("<html><b>+</b></html>") {
    tooltip = Loc("newTweet")
  }
  contents += new Button(Loc("profile"))
  contents += new Button(Loc("messages"))
  contents += new Button(Loc("whoToFollow"))
  contents += addTweet
  listenTo(addTweet)
  val parent = new TextField(10)
  reactions += {
    case ButtonClicked(`addTweet`) => new NewTweetWindow
  }
}
