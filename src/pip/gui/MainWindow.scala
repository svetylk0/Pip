package pip.gui

import pip.core.{Loc, Tools, Auth, PipCore}
import swing._

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 6.3.11
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */

object MainWindow extends SimpleSwingApplication {
  /**
   * Cast cvicneho nacteni dat
   */
  val tw = Auth.authorizedTwitterInstance(Auth.loadAccessToken("myauth"))
  val core = new PipCore(tw)

  println("Je Twitter.com dostupny? " + {
    if (Tools.isConnectionAvailable) "ano" else "ne"
  })

  Loc.load("czech.loc")

  /**
   * Konec cvicne casti
   */

  def top = new MainFrame {
    val parent = new TextField(10)

    contents = new BoxPanel(Orientation.Vertical) {
      contents ++= core.homeTimelineFutures map {
        tweet =>
          new TweetView(tweet())
      }
      contents += new Button {
        action = Action("Click") {
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
                        "..\\..\\res\\zpevacek_icon.jpg")).getImage
    }
  }
}
