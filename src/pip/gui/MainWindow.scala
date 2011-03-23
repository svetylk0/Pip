package pip.gui

import swing._
import event._
import scala.swing.event.Key._
import java.io.File
import javax.swing.{ImageIcon, UIManager}
import pip.core._
import swing.TabbedPane.Page
import actors.Future
import actors.Actor.actor
import javax.swing.border.EmptyBorder
import java.awt.Font

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

  val notification = Notifications.simpleNotification(Loc("loginInProgress"))

//  Notifications.animatedRightDownCornerSimpleNotification("Hellloooo :-)")

  val tw = if (fileExists(authFile)) {
    Auth.authorizedTwitterInstance(Auth.loadAccessToken(authFile))
  } else {
    val (t, s) = Auth.tokenStringAndSecret(new AuthDialog(Auth.authURL) getPin)
    Auth.saveAccessToken(t, s, authFile)
    Auth.authorizedTwitterInstance(t, s)
  }

  val core = new PipCore(tw)

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  val tweetPager = new TweetPager(tweetsPerPage, core.homeTimelineFutures)
  val mentionsPager = new TweetPager(tweetsPerPage, core.mentionsFutures)

  private def getTweetPanelByPager(tabPane: TabbedPane,
                                   pager: TweetPager[Future[Tweet]]) = new BoxPanel(Orientation.Vertical) with RefreshableBoxPanel {

    val twPanel = new BoxPanel(Orientation.Vertical) {
      contents ++= pager.firstPage
    }

    val defaultPager = pager
    val defaultPanel = twPanel

    contents += scrollPane(twPanel)
    contents += new Toolbar(tabPane, twPanel, pager)
  }

  def scrollPane(c: Component) = new ScrollPane(c) {
    //horizontalScrollBarPolicy = ScrollPane.BarPolicy.Never
    verticalScrollBar.unitIncrement = 10
  }

  notification.setText(Loc("loadingTweets"))

  val tabs = new TabbedPane {
    val tweetPanel = getTweetPanelByPager(this, tweetPager)
    val mentionsPanel = getTweetPanelByPager(this, mentionsPager)

    private val tabsRef = this
    val searchPanel = new BoxPanel(Orientation.Vertical) {
      val searchText = new TextField(10)

      object SearchButton extends Button {
        text = Loc("search")
      }

      val topPanel = new FlowPanel(FlowPanel.Alignment.Left)(searchText, SearchButton)

      contents += topPanel

      listenTo(SearchButton)

      reactions += {
        case ButtonClicked(SearchButton) =>
          thread {
            SearchButton.enabled = false
            contents.clear
            contents += topPanel

            //nejsou k dispozici stranky, tudiz ani pager
            contents += scrollPane(new BoxPanel(Orientation.Vertical) {
              try {
                contents ++= core.searchAsFutures(searchText.text)
              } catch {
                //osetreni pripadu selhani, napr. kvuli pretizeni twitteru
                case e: Exception =>
                  contents += new Label {
                    font = font.deriveFont(Font.BOLD, 20f)
                    text = Loc("searchFailed")
                    border = new EmptyBorder(20, 10, 20, 10)
                  }
              }
            })
            
            SearchButton.enabled = true
            MainWindow.repaint
          }
      }

    }

    pages += new TabbedPane.Page(Loc("tweets"), tweetPanel) {
      mnemonic = Key1.##
    }

    pages += new TabbedPane.Page(Loc("mentions"), mentionsPanel) {
      mnemonic = Key2.##
    }

    pages += new TabbedPane.Page(Loc("searchTitle"), searchPanel) {
    }
  }

  def refreshActiveTab() {
    tabs.selection.index match {
      case 0 => tabs.tweetPanel.refresh()
      case 1 => tabs.mentionsPanel.refresh()
      case 2 => //nerefreshuje se
      case _ =>
    }
    tabs.repaint
  }

  def repaint() {
    mainFrame.repaint()
  }

  notification.setText(Loc("loadingMainWindow"))

  val mainFrame = new MainFrame {

    val scrollViewport = new ScrollPane(tabs) {
      //horizontalScrollBarPolicy = ScrollPane.BarPolicy.Never
      verticalScrollBar.unitIncrement = 10
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += tabs
      //      contents += scrollViewport
      //      contents += Toolbar
    }

    title = Loc("pip")
    minimumSize = new Dimension(tabs.size.width + scrollViewport.verticalScrollBar.size.width, tabs.size.height)
    iconImage = (new ImageIcon("res" + separator + "zpevacek_icon.jpg")).getImage
  }

  def top = mainFrame

  notification.dispose
}
