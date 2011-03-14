package pip.gui

import scala.swing._

object Toolbar extends BoxPanel(Orientation.Horizontal) {
      
      contents += new Button(Loc("home"))
      contents += new Button(Loc("profile"))
      contents += new Button(Loc("messages"))
      contents += new Button(Loc("who to follow"))
}
