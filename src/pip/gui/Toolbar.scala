package pip.gui

import scala.swing._
import scala.swing.event._
import swing.TabbedPane.Page
import pip.core.{Tweet, TweetPager, Implicits, Loc}
import actors.Future

class Toolbar(tab: TabbedPane,
              tweetPanel: BoxPanel,
              pager: TweetPager[Future[Tweet]],
//              withAddTweetButton: Boolean = true) extends BoxPanel(Orientation.Horizontal) {
              withAddTweetButton: Boolean = true) extends FlowPanel(FlowPanel.Alignment.Center)() {

  import MainWindow.core
  import Implicits._

  object UpButton extends Button(Loc("up")) {
    mnemonic = Key.N
    tooltip = Loc("goToFirstPage")
  }

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

  object PreferencesButton extends Button(Loc("preferences"))

  contents += PreferencesButton
  contents += new Button(Loc("messages"))
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
