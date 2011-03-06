package pip.gui
import scala.swing._
import actors.Actor.actor
import pip.core.Loc

object LoginView extends SimpleSwingApplication {
  Loc.load("czech.loc")

  val parent = new FlowPanel {
    contents += new Button("OK")
    contents += new Label("Uzivatel")
  }

  def top = new MainFrame {
    contents = new FlowPanel {
      contents += new Button {
        action = Action("click") {
         /* Dialog.showConfirmation(parent,
                 message = "Please login",
                 title = "Login",
                 messageType = Dialog.Message.Plain,
                 optionType = Dialog.Options.OkCancel,
                 entries = Seq("Login", "Cancel"),
                 initial = 1)*/
          actor {
            println("Pin je: "+AuthDialog.pin)
          }
        }
      }
   }
 }
}

