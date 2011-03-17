package pip.gui

import pip.core.Loc
import scala.swing._
import scala.swing.event._

object Toolbar extends BoxPanel(Orientation.Horizontal) {

  import MainWindow.core

  object AddTweetButton extends Button("<html><b>+</b></html>") {
    tooltip = Loc("newTweet")
  }

  object NextPageButton extends Button("<html><b>&gt;</b></html>") {
    tooltip = Loc("nextPage")
  }

  object PrevPageButton extends Button("<html><b>&lt;</b></html>") {
    tooltip = Loc("prevPage")
  }

  contents += new Button(Loc("profile"))
  contents += new Button(Loc("messages"))
  contents += PrevPageButton
  contents += NextPageButton
  contents += AddTweetButton

  val parent = new TextField(10)

  listenTo(Toolbar.AddTweetButton)

  reactions += {
    case ButtonClicked(Toolbar.AddTweetButton) => new NewTweetWindow(core, this)
  }

}
