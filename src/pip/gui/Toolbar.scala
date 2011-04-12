package pip.gui

import scala.swing._
import scala.swing.event._
import pip.core.{Globals, TweetPager, Implicits, Loc}

class Toolbar(tweetPanel: BoxPanel,
              pager: TweetPager) extends FlowPanel(FlowPanel.Alignment.Center)() {

  import MainWindow.core
  import Globals._
  import Implicits._

  object UpButton extends FlatButton {
    mnemonic = Key.N
    icon = firstIcon
    tooltip = Loc("goToFirstPage")
  }

  object AddTweetButton extends FlatButton {
    mnemonic = Key.N
    icon = addIcon
    tooltip = Loc("newTweet")
  }

  object NextPageButton extends FlatButton {
    mnemonic = Key.Right
    icon = rightIcon
    tooltip = Loc("nextPage")
  }

  object PrevPageButton extends FlatButton {
    enabled = false
    mnemonic = Key.Left
    icon = leftIcon
    tooltip = Loc("prevPage")
  }

  object PreferencesButton extends FlatButton {
    icon = preferencesIcon
    tooltip = Loc("preferences")
  }

  object QuitButton extends FlatButton {
    icon = quitIcon
    tooltip = Loc("quit")
  }

  object MessagesButton extends FlatButton {
    icon = messagesIcon
    tooltip = Loc("messages")
  }

  contents += AddTweetButton
  contents += UpButton
  contents += PrevPageButton
  contents += NextPageButton
  contents += MessagesButton
  contents += PreferencesButton
  contents += QuitButton

  val parent = new TextField(10)

  listenTo(AddTweetButton, PrevPageButton, NextPageButton, UpButton, PreferencesButton,
           QuitButton)

  reactions += {
    case ButtonClicked(QuitButton) => sys.exit()
    case ButtonClicked(PreferencesButton) => (new PreferencesWindow(MainWindow.mainFrame)).visible = true
    case ButtonClicked(AddTweetButton) => new NewTweetWindow(core, MainWindow.mainFrame)
    case ButtonClicked(PrevPageButton) =>
      if (pager.page == 2) PrevPageButton.enabled = false

      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.previousPage()

      MainWindow.repaint

    case ButtonClicked(NextPageButton) =>
      PrevPageButton.enabled = true

      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.nextPage()

      MainWindow.repaint

    case ButtonClicked(UpButton) =>
      PrevPageButton.enabled = false
      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.firstPage()

      MainWindow.repaint
  }

}
