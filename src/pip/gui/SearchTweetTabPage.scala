package pip.gui

import java.awt.Dimension
import swing._
import event.{ButtonClicked, Key, KeyPressed}
import pip.core.{Tools, Globals, TweetListPager, Loc}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 11.4.11
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */


object SearchTweetListPager extends TweetListPager(Globals.tweetsPerPage)


class SearchTweetTabPage(title: String) extends TweetTabPage(title, SearchTweetListPager) {
  import Tools.thread
  import MainWindow.core

  object SearchText extends TextField(10)

  object SearchButton extends FlatButton {
    tooltip = Loc("search")
    icon = Globals.searchIcon
  }

  innerPanel.listenTo(SearchButton, SearchText.keys)

  innerPanel.reactions += {
    case KeyPressed(SearchText, key, _, _) =>
      if (key == Key.Enter) thread {
        SearchButton.doClick
      }

    case ButtonClicked(SearchButton) =>
      thread {
        SearchButton.enabled = false
        SearchText.enabled = false

        try {
          val searchResult = core.searchAsFutures(SearchText.text)
          if (searchResult.isEmpty) {
            Notifications.animatedRightDownCornerSimpleNotification(Loc("noSearchResults"))
          } else {
            SearchTweetListPager.tweetList = searchResult
            innerPanel.refresh()
          }
        } catch {
          //        osetreni pripadu selhani, napr. kvuli pretizeni twitteru
          case e: Exception =>
            Notifications.animatedRightDownCornerSimpleNotification(Loc("searchFailed"))
            e.printStackTrace
        }

        SearchButton.enabled = true
        SearchText.enabled = true
        MainWindow.repaint
      }
  }

  innerPanel.requestFocus

  val topPanel = new FlowPanel(FlowPanel.Alignment.Left)(SearchText, SearchButton) {
    maximumSize = new Dimension(Int.MaxValue, SearchButton.preferredSize.getHeight.toInt + 5)
  }

  innerPanel.addNorth(topPanel)
  //  contents += topPanel


//  def infoLabel(text: String) = new FlowPanel(FlowPanel.Alignment.Center)() {
//    content = new Label(text) {
//      font = font.deriveFont(Font.BOLD, 20f)
//      border = new EmptyBorder(20, 10, 20, 10)
//    }
//  }


}