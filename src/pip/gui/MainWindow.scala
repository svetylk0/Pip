package pip.gui

import swing._
import javax.swing.{ImageIcon, UIManager}
import pip.core._
import java.io.File

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
  //nejdrive nacist vse potrebne
  Config.loadConfig()

  Loc.load(Config("language"))

  val notification = Notifications.simpleNotification(Loc("loginInProgress"))

  val tw = if (fileExists(authFile)) {
    Auth.authorizedTwitterInstance(Auth.loadAccessToken(authFile))
  } else {
    val (t, s) = Auth.tokenStringAndSecret(new AuthDialog(Auth.authURL) getPin)
    Auth.saveAccessToken(t, s, authFile)
    Auth.authorizedTwitterInstance(t, s)
  }

  val core = new PipCore(tw)
  
  //ulozeni screen name
  Globals.myNick = core.screenName

  //nastaveni L&F z configu
  private def setSystemLookAndFeel = UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  Config("lookAndFeel") match {
    case "System" => setSystemLookAndFeel
    case lafName =>
      UIManager.getInstalledLookAndFeels find { _.getName == lafName } match {
        case Some(lafInfo) => UIManager.setLookAndFeel(lafInfo.getClassName)
        case None => setSystemLookAndFeel
      }
  }


  val tweetPager = new TweetPager(tweetsPerPage) {
    val f = core.homeTimelineFutures _
  }

  val mentionsPager = new TweetPager(tweetsPerPage) {
    val f = core.mentionsFutures _
  }

//  private def getTweetPanelByPager(tabPane: TabbedPane,
//                                   pager: TweetPager[Future[Tweet]]) = new BorderPanel with RefreshablePanel {
//
//    val twPanel = new BoxPanel(Orientation.Vertical) {
//      contents ++= pager.firstPage
//    }
//
//    val defaultPager = pager
//    val defaultPanel = twPanel
//
//    add(twPanel, BorderPanel.Position.Center)
//    add(new Toolbar(tabPane, twPanel, pager), BorderPanel.Position.South)
//  }

  def scrollPane(c: Component) = new ScrollPane(c) {
    horizontalScrollBarPolicy = ScrollPane.BarPolicy.Never
    verticalScrollBar.unitIncrement = 10
  }

  notification.setText(Loc("loadingTweets"))

  val tabs = new TabbedPane {

    val tweetsPage = new TweetTabPage(Loc("tweets"), tweetPager)
    val mentionsPage = new TweetTabPage(Loc("mentions"), mentionsPager)
    val searchPage = new SearchTweetTabPage(Loc("searchTitle"))

    val pageList = List(tweetsPage, mentionsPage, searchPage)

    pages ++= pageList

   /* pages += new TabbedPane.Page(Loc("tweets"), tweetPanel) {
      mnemonic = Key1.##
    }

    pages += new TabbedPane.Page(Loc("mentions"), mentionsPanel) {
      mnemonic = Key2.##
    }

    pages += new TabbedPane.Page(Loc("searchTitle"), searchPanel) {
    }*/
  }

  def refreshActiveTab() {
    tabs.pageList(tabs.selection.index).refresh()
//    tabs.selection.index match {
//      case 0 => tabs.tweetPanel.refresh()
//      case 1 => tabs.mentionsPanel.refresh()
//      case 2 => //nerefreshuje se
//      case _ =>
//    }
    tabs.repaint
  }

  def repaint() {
    mainFrame.repaint()
  }

  notification.setText(Loc("loadingMainWindow"))

  //plain load, pro rychlejsi nacteni pozdeji
  new NewTweetWindow(core, new MainFrame, autoVisible = false)

  val mainFrame = new MainFrame {

    val scrollViewport = new ScrollPane(tabs) {
      horizontalScrollBarPolicy = ScrollPane.BarPolicy.Never
      verticalScrollBar.unitIncrement = 10
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += tabs
      //      contents += scrollViewport
      //      contents += Toolbar
    }

    title = Loc("pip")
//    minimumSize = new Dimension(tabs.size.width + scrollViewport.verticalScrollBar.size.width, tabs.size.height)
    iconImage = (new ImageIcon("res" + separator + "zpevacek_icon.png")).getImage
  }

  def top = mainFrame

  notification.dispose
}
