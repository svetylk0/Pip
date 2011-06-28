package pip.gui

import swing._
import pip.core.{Implicits, TweetPager}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 11.4.11
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */

class TweetTabPage(title: String,
                   pager: TweetPager) extends TabbedPane.Page(title, new BorderPanel) {

  import Implicits._

  protected val innerPanel = new BorderPanel with RefreshablePanel {

    def addNorth(c: Component) = add(c, BorderPanel.Position.North)

    val defaultPager = pager
    val defaultPanel = new BoxPanel(Orientation.Vertical) {
      contents ++= defaultPager.firstPage
    }

    add(defaultPanel, BorderPanel.Position.Center)
    add(new Toolbar(defaultPanel, pager), BorderPanel.Position.South)
  }

  def refresh() {
    innerPanel.refresh()
  }

  content = innerPanel
}