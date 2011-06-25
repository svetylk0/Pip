package pip.core

import twitter4j.{User, Status}
import javax.swing.ImageIcon
import pip.gui.{RawImageService, NoServiceAvailable, TwitpicService, YfrogService}
import java.net.URL

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */

case class Tweet(originalStatus: Status) {
  import Implicits._
  
  val status = if (originalStatus.isRetweet) originalStatus.getRetweetedStatus 
  			   else originalStatus

  val user = status.getUser

  val id = status.getId
  val isFavorited = status.isFavorited
  val isRetweet = originalStatus.isRetweet
  val retweetedBy = originalStatus.getUser.getScreenName
  
  val isRetweetedByMe = (isRetweet,retweetedBy == Globals.myNick) match {
    case (true,true) => true
    case _ => false
  }

  val name = user.getName
  val nick = user.getScreenName
  val text = status.getText

  val retweetCount = status.getRetweetCount

  def cachedImageLoad(url: URL) = {
    val file = url.fileName
    if (ImagesCache.contains(file)) ImagesCache.get(file)
    else {
      ImagesCache.save(url,file)
      ImagesCache.get(file)
    }
  }
  
//(new ImageIcon(user.getProfileImageURL))  
  val profileIcon = cachedImageLoad(user.getProfileImageURL).scale(48,48)

  val urlList = status.getURLEntities match {
    case null => Nil
    case x => 
      x.toList map { entity =>
        entity.getExpandedURL match {
          case null => entity.getURL.toString
          case x => x.toString
        }
      }
  }

  //getURLEntities zrejme nefunguje, docasna nahrazka:

  private val urlReg = """\w+://\S+""".r
  private val yfrogReg = """(http://yfrog.com/.+)""".r
  private val twitpicReg = """(http://twitpic.com/.+)""".r
  private val rawimageReg = """(http://.+?(gif|jpg|jpeg|png))""".r

//  val urlList = urlReg findAllIn text toList

  //val hrefList1 = urlList map("<html><a href=\""+ _ +"\">")
  //val hrefList2 = urlList map (_ +"</a></html>")
  //val hrefList = List.concat(hrefList1, hrefList2) mkString ""
  val decomposed = text.split(" ")
  for (i <- decomposed)
    urlReg replaceAllIn(i, "<html><a href=\""+ urlReg +">"+ urlReg +"</a></html>")
  //for (i <- decomposed) println("new "+ i)

  val imagesList = urlList collect {
//zatim nefunkcni - neco zmenili a vzdy dostanu  java.net.ProtocolException: Server redirected too many  times (20)
//    case yfrogReg(url) => new YfrogService(url)
    case twitpicReg(url) => new TwitpicService(url)
    case rawimageReg(url,_) => new RawImageService(url)
    case _ => NoServiceAvailable
  } filterNot { _ == NoServiceAvailable }

  def containsURLs = !urlList.isEmpty
  def containsImages = !imagesList.isEmpty
}
