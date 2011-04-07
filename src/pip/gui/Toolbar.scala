package pip.gui

import scala.swing._
import scala.swing.event._
import swing.TabbedPane.Page
import pip.core.{Globals, Tweet, TweetPager, Implicits, Loc}
import actors.Future

class Toolbar(tab: TabbedPane,
              tweetPanel: BoxPanel,
              pager: TweetPager[Future[Tweet]],
              withAddTweetButton: Boolean = true) extends FlowPanel(FlowPanel.Alignment.Center)() {

  import MainWindow.core
  import Globals._
  import Implicits._

  object UpButton extends Button {
    mnemonic = Key.N
    icon = firstIcon
    tooltip = Loc("goToFirstPage")
  }

  object AddTweetButton extends Button {
    mnemonic = Key.N
    icon = addIcon
    tooltip = Loc("newTweet")
  }

  object NextPageButton extends Button {
    mnemonic = Key.Right
    icon = rightIcon
    tooltip = Loc("nextPage")
  }

  object PrevPageButton extends Button {
    enabled = false
    mnemonic = Key.Left
    icon = leftIcon
    tooltip = Loc("prevPage")
  }

  object PreferencesButton extends Button {
    icon = preferencesIcon
    tooltip = Loc("preferences")
  }

  object MessagesButton extends Button {
    icon = messagesIcon
    tooltip = Loc("messages")
  }

  contents += PreferencesButton
  contents += MessagesButton
  contents += UpButton
  contents += PrevPageButton
  contents += NextPageButton

  if (withAddTweetButton) contents += AddTweetButton

  val parent = new TextField(10)

  listenTo(AddTweetButton, PrevPageButton, NextPageButton, UpButton, PreferencesButton)

  reactions += {
    case ButtonClicked(PreferencesButton) => (new PreferencesWindow(MainWindow.mainFrame)).visible = true
    case ButtonClicked(AddTweetButton) => new NewTweetWindow(core, MainWindow.mainFrame)
    case ButtonClicked(PrevPageButton) =>
      if (pager.page == 2) PrevPageButton.enabled = false

      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.previousPage()

      tab.repaint

    case ButtonClicked(NextPageButton) =>
      PrevPageButton.enabled = true

      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.nextPage()

      tab.repaint

    case ButtonClicked(UpButton) =>
      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.firstPage()

      tab.repaint
  }

}
