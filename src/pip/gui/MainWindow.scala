package pip.gui

import swing._
import event._
import scala.swing.event.Key._
import java.io.File
import javax.swing.{ImageIcon, UIManager}
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
  import Tools._
  import Globals._
  import Implicits.convertFutureTweetToTweetView

  //nejdrive nacist vse potrebne
  Config.loadConfig()

  Loc.load(Config("language"))

  val tw = if (fileExists(authFile)) {
    Auth.authorizedTwitterInstance(Auth.loadAccessToken(authFile))
  } else {
    val (t,s) = Auth.tokenStringAndSecret(new AuthDialog(Auth.authURL) getPin)
    Auth.saveAccessToken(t,s,authFile)
    Auth.authorizedTwitterInstance(t,s)
  }

  val core = new PipCore(tw)

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  val mainFrame = new MainFrame {
    val tweetPager = new TweetPager(tweetsPerPage,core.homeTimelineFutures)
    val mentionsPager = new TweetPager(tweetsPerPage,core.mentionsFutures)

    val tweetPanel = new BoxPanel(Orientation.Vertical) {
      contents ++= tweetPager.firstPage
    }

    val mentionsPanel = new BoxPanel(Orientation.Vertical) {
      contents ++= mentionsPager.firstPage
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
      verticalScrollBar.unitIncrement = 10
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += scrollViewport
      contents += Toolbar
    }

    title = Loc("pip")
    minimumSize = new Dimension(tabs.size.width + scrollViewport.verticalScrollBar.size.width, tabs.size.height)
    iconImage = (new ImageIcon("res"+ separator +"zpevacek_icon.jpg")).getImage
  }

  def top = mainFrame
}
