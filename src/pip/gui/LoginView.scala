package pip.gui
import scala.swing._

object LoginView extends SimpleSwingApplication {
  val parent = new FlowPanel {
    contents += new Button("OK")
    contents += new Label("Uzivatel")
  }

  def top = new MainFrame {
    contents = new FlowPanel {
      contents += new Button {
        action = Action("click") {
          Dialog.showInput(parent,
                 message = "Please login",
                 title = "Login",
                 messageType = Dialog.Message.Plain,
                 optionType = Dialog.Options.OkCancel,
                 entries = Seq("Login", "Cancel"),
                 initial = 1)
        }
      }
   }
 }
}

