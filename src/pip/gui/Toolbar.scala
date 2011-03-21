package pip.gui

import scala.swing._
import scala.swing.event._
import swing.TabbedPane.Page
import pip.core.{Tweet, TweetPager, Implicits, Loc}
import actors.Future

class Toolbar(tab: TabbedPane,
              tweetPanel: BoxPanel,
              pager: TweetPager[Future[Tweet]]) extends BoxPanel(Orientation.Horizontal) {

  import MainWindow.core
  import Implicits._

  object AddTweetButton extends Button("+".tagB.tagHtml) {
    mnemonic = Key.N
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

  listenTo(AddTweetButton, PrevPageButton, NextPageButton)

  reactions += {
    case ButtonClicked(AddTweetButton) => new NewTweetWindow(core, this)
    case ButtonClicked(PrevPageButton) =>
      if (pager.page == 2) PrevPageButton.enabled = false

      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.previousPage()
      tweetPanel.contents += this

      tab.repaint

    case ButtonClicked(NextPageButton) =>
      PrevPageButton.enabled = true

      tweetPanel.contents.clear
      tweetPanel.contents ++= pager.nextPage()
      tweetPanel.contents += this

      tab.repaint
  }

}
