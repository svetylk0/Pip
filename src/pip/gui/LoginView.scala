package pip.gui
import scala.swing._

object LoginView extends SimpleSwingApplication {
  val parent = new TextField(10)

  def top = new MainFrame {
    contents = new FlowPanel {
      contents += new Button {
        action = Action("click") {
          Dialog.showInput(parent,
                 message = "Uzivatelske jmeno",
                 title = "Prihlaseni",
                 messageType = Dialog.Message.Plain,
                 initial = "")
        }
      }
   }
 }
}

