package pip.gui

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import scala.swing.event.SelectionChanged
import scala.swing.event.MouseClicked
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
  import Globals._
  
  object SearchText extends TextField(10)

  object SavedSearch extends MutableComboBox(core.savedSearch)
  
  object SearchButton extends FlatButton {
    tooltip = Loc("search")
    icon = Globals.searchIcon
  }

  object AddSearchButton extends FlatButton {
    mnemonic = Key.N
    icon = addIcon
    tooltip = Loc("addSavedSearch")
  }
  
  innerPanel.listenTo(SearchButton, SearchText.keys, AddSearchButton)
  
  //AWT reakce na vyber
  SavedSearch.peer.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) {
      SearchText.text = SavedSearch.item
      thread {
        SearchButton.doClick
      }
    }
  })

  innerPanel.reactions += {
    case KeyPressed(SearchText, key, _, _) =>
      if (key == Key.Enter) thread {
        SearchButton.doClick
      }

    case ButtonClicked(AddSearchButton) =>
      if (SearchText.text != "") {
        try {
          core.addSavedSearch(SearchText.text)
          SavedSearch.add(SearchText.text)
          Notifications.animatedRightDownCornerSimpleNotification(Loc("addSavedSearchOk"))
        } catch {
          case e: Exception => Notifications.animatedRightDownCornerSimpleNotification(Loc("addSavedSearchFailed"))
        }
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

  val topPanel = new FlowPanel(FlowPanel.Alignment.Left)(SearchText, SavedSearch, SearchButton, AddSearchButton) {
    maximumSize = new Dimension(Int.MaxValue, SearchButton.preferredSize.getHeight.toInt + 5)
  }

  innerPanel.addNorth(topPanel)
}