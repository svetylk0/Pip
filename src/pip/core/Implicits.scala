package pip.core

import actors.Future
import pip.gui.TweetView
import java.io.{InputStreamReader, InputStream}
import collection.immutable.PagedSeq
import java.net.URL
import javax.swing.ImageIcon

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 16.3.11
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

object Implicits {
  //rozsireni URL
  implicit def richURL(u: URL) = new {
    def fileName = u.getPath.split("/").last
  }
  
  //rozsireni pro uzavirani stringu do HTML tagu
  implicit def encloseStringInHtmlTag(s: String) = new {
    private def simpleTag(tag: String) = "<"+tag+">"+s+"</"+tag+">"

    def tagA = "<a href=\""+s+"\">"+s+"</a>"
    def tagB = simpleTag("b")
    def tagU = simpleTag("u")
    def tagHtml = simpleTag("html")
  }

  //automaticka konverze List[Future[Tweet]] na List[TweetView]
  implicit def convertFutureTweetToTweetView(in: List[Future[Tweet]]) = in map {
    tweet => new TweetView(tweet())
  }
  
  //totez, akorat pro Tweet
  implicit def convertTweetToTweetView(in: List[Tweet]) = in map {
    tweet => new TweetView(tweet)
  }

  implicit def wrapInputStream[T <: InputStream](is: T) =
    PagedSeq.fromReader(new InputStreamReader(is,"UTF-8"))
//    PagedSeq.fromReader(new InputStreamReader(is,encoding))
    
  //zmena velikosti obrazku
  implicit def scaleImageIcon(icon: ImageIcon) = new {
    def scale(width: Int, height: Int) = {
      new ImageIcon(icon.getImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH))
    }
  }

}