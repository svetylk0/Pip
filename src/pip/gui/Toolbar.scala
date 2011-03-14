package pip.gui

import scala.swing._

object Toolbar extends BoxPanel(Orientation.Horizontal) {
      
      contents += new Button("Home")
      contents += new Button("Profile")
      contents += new Button("Messages")
      contents += new Button("Who to follow")
}
