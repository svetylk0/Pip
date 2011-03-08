package pip.gui

import java.net._
import javax.swing.ImageIcon
import scala.swing._

object LoginView extends SimpleSwingApplication {
  val parent = new TextField(10)
  val fileSeparator = System.getProperty("file.separator")

  def top = new MainFrame {
    contents = new FlowPanel {
      contents += new Button {
        action = Action("click") {
          Dialog.showInput(parent,
                 message = "Enter your PIN, please",
                 title = "Login",
                 messageType = Dialog.Message.Plain,
                 initial = "")
        }
	doClick
	visible = false
     }
     iconImage = Swing.Icon(this.getClass.getResource(
                ".."+ fileSeparator +".."+ fileSeparator +"res"+ fileSeparator +                "zpevacek_icon.jpg")).getImage
   }
 }
}

