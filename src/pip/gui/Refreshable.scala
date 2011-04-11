package pip.gui

import swing.Component
import collection.mutable.Buffer
import pip.core.{Implicits, TweetPager}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 20.3.11
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */

trait RefreshablePanel {
  import Implicits.convertFutureTweetToTweetView

  val defaultPager: TweetPager
  val defaultPanel: { val contents: Buffer[Component] }

  def refresh() {
    val loadPage = defaultPager.currentPage()
    defaultPanel.contents.clear
    defaultPanel.contents ++= loadPage
  }
}