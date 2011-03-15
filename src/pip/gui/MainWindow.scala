package pip.gui

import swing._
import scala.swing.event.Key._
import java.io.File
import javax.swing.ImageIcon
import pip.core._

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 6.3.11
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */

object MainWindow extends SimpleSwingApplication {

  import File.separator
  import Globals._

  /**
   * Cast cvicneho nacteni dat
   */
  val tw = Auth.authorizedTwitterInstance(Auth.loadAccessToken("myauth"))
  val core = new PipCore(tw)

  //  println("Je Twitter.com dostupny? " + {
  //    if (Tools.isConnectionAvailable) "ano" else "ne"
  //  })

  Loc.load("czech.loc")
  Config.loadConfig
  Globals.setConfigVariables
  /**
   * Konec cvicne casti
   */

  def top = new MainFrame {
    val parent = new TextField(10)

    val pin = Dialog.showInput(
      parent,
      message = Loc("enterPIN"),
      title = Loc("Login"),
      messageType = Dialog.Message.Plain,
      initial = ""
    )

    val tweetPager = new TweetPager(tweetsPerPage,core.homeTimelineFutures)
    val mentionsPager = new TweetPager(tweetsPerPage,core.mentionsFutures)

    val tweetPanel = new BoxPanel(Orientation.Vertical) {
      //docasne budeme nacitat jen 5 tweetu, at vidime jak to bude vypadat
      //pak se to da pryc :-)
      contents ++= tweetPager.firstPage map {
        tweet => new TweetView(tweet())
      }
    }

    val mentionsPanel = new BoxPanel(Orientation.Vertical) {
      //docasne budeme nacitat jen 5 tweetu, at vidime jak to bude vypadat
      //pak se to da pryc :-)
      contents ++= mentionsPager.firstPage map {
        tweet => new TweetView(tweet())
      }
    }

    val tabs = new TabbedPane {
      pages += new TabbedPane.Page(Loc("tweets"), tweetPanel) {
        mnemonic = Key1.##
      }
      pages += new TabbedPane.Page(Loc("mentions"), mentionsPanel) {
        mnemonic = Key2.##
      }
    }
    val scrollViewport = new ScrollPane(tabs) {
      //horizontalScrollBarPolicy = ScrollPane.BarPolicy.Never
    }
    val toolbar = Toolbar
    contents = new BoxPanel(Orientation.Vertical) {
      contents += scrollViewport
      contents += toolbar
    }

    title = Loc("pip")
    minimumSize = toolbar.size // + scrollViewport.verticalScrollBar.size
    iconImage = (new ImageIcon("res"+ separator +"zpevacek_icon.jpg")).getImage
  }
}
