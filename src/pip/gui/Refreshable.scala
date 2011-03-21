package pip.gui

import actors.Future
import swing.{BoxPanel, Panel}
import pip.core.{Implicits, Tweet, TweetPager}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 20.3.11
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */

trait RefreshableBoxPanel {
  import Implicits.convertFutureTweetToTweetView

  val defaultPager: TweetPager[Future[Tweet]]
  val defaultPanel: BoxPanel

  def refresh() {
    defaultPanel.contents.clear
    defaultPanel.contents ++= defaultPager.currentPage()
  }
}