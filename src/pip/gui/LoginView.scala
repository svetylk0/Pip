package pip.gui
import scala.swing.{Button, Dialog, MainFrame, SimpleSwingApplication}

object LoginView extends Dialog {
    title = "Dialog test"
    modal = true
    defaultButton = new Button("OK")
}

