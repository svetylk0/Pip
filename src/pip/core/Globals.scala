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

  //ikonky
  val replyIcon = loadIcon("reply.png")
  val replyHighlightIcon = loadIcon("reply_highlight.png")
  val retweetIcon = loadIcon("retweet.png")
  val retweetHighlightIcon = loadIcon("retweet_highlight.png")
  val favoriteIcon = loadIcon("favorite.png")
  val favoriteHighlightIcon = loadIcon("favorite_highlight.png")

  def setConfigVariables() {
    tweetsPerPage = Config("tweetsPerPage").toInt
  }
}