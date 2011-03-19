package pip.core

import actors.Future
import pip.gui.TweetView
import java.awt.Color

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 16.3.11
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

object Implicits {
  //rozsireni pro uzavirani stringu do HTML tagu
  implicit def encloseStringInHtmlTag(s: String) = new {
    def tagA = "<a href=\""+s+"\">"+s+"</a>"
    def tagB = "<b>"+s+"</b>"
    def tagHtml = "<html>"+s+"</html>"
  }

  //automaticka konverze List[Future[Tweet]] na List[TweetView]
  implicit def convertFutureTweetToTweetView(in: List[Future[Tweet]]) = in map {
    tweet => new TweetView(tweet())
  }

}