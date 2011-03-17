package pip.gui

import scala.swing._
import scala.swing.event._
import pip.core.{Implicits, Loc}

object Toolbar extends BoxPanel(Orientation.Horizontal) {

  import MainWindow.core
  import Implicits._

  object AddTweetButton extends Button("+".tagB.tagHtml) {
    tooltip = Loc("newTweet")
  }

  object NextPageButton extends Button("&gt;".tagB.tagHtml) {
    mnemonic = Key.Right
    tooltip = Loc("nextPage") +", "+ Loc("altRightArrow")
  }

  object PrevPageButton extends Button("&lt;".tagB.tagHtml) {
    enabled = false
    mnemonic = Key.Left
    tooltip = Loc("prevPage") +", "+ Loc("altLeftArrow")
  }

  contents += new Button(Loc("profile"))
  contents += new Button(Loc("messages"))
  contents += PrevPageButton
  contents += NextPageButton
  contents += AddTweetButton

  val parent = new TextField(10)

  listenTo(Toolbar.AddTweetButton, PrevPageButton, NextPageButton)

  import MainWindow.mainFrame.{tabs, tweetPanel, tweetPager, mentionsPanel, mentionsPager}

  reactions += {
    case ButtonClicked(AddTweetButton) => new NewTweetWindow(core, this)
    case ButtonClicked(PrevPageButton) =>
      tabs.selection.index match {
        case 0 =>
          tweetPanel.contents.clear
          tweetPanel.contents ++= tweetPager.previousPage()
	  if (tweetPager.page == 1) PrevPageButton.enabled = false
        case 1 =>
          mentionsPanel.contents.clear
          mentionsPanel.contents ++= mentionsPager.previousPage()
        case _ =>
      }
      tabs.repaint

    case ButtonClicked(NextPageButton) =>
      PrevPageButton.enabled = true
    tabs.selection.index match {
      case 0 =>
        tweetPanel.contents.clear
        tweetPanel.contents ++= tweetPager.nextPage()
      case 1 =>
        mentionsPanel.contents.clear
        mentionsPanel.contents ++= mentionsPager.nextPage()
      case _ =>
    }
    tabs.repaint
  }

}
