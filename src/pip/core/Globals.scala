package pip.core

import java.io.File
import javax.swing.ImageIcon
import swing.{Orientation, BoxPanel, Separator, Label}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 27.2.11
 * Time: 9:38
 * To change this template use File | Settings | File Templates.
 */

object Globals {
  import Tools.loadIcon

  val authFile = "myauth"

  val configFile = "config.ini"
  val defaultTweetCount = 20
  val encoding = "UTF-8"

  val backgroundColor = (new Label).background
  val localizationDir = "loc"
  val resourcesDir = "res"

  //promenne ze souboru config.ini
  var tweetsPerPage = 5
  var browserCommand = ""

  //ikonky
  val replyIcon = loadIcon("reply.png")
  val replyHighlightIcon = loadIcon("reply_highlight.png")
  val retweetIcon = loadIcon("retweet.png")
  val retweetHighlightIcon = loadIcon("retweet_highlight.png")
  val retweetHighlightIcon2 = loadIcon("retweet_highlight2.png")
  val favoriteIcon = loadIcon("favorite.png")
  val favoriteHighlightIcon = loadIcon("favorite_highlight.png")
  val favoriteHighlightIcon2 = loadIcon("favorite_highlight2.png")
  val urlIcon = loadIcon("url.png")
  val urlHighLightIcon = loadIcon("url_highlight.png")


  //tlacitka mysi
  val leftMouseButton = 0
  val middleMouseButton = 512
  val rightMouseButton = 256

  def setConfigVariables() {
    tweetsPerPage = Config("tweetsPerPage").toInt
    browserCommand = Config("browserCommand")
  }
}