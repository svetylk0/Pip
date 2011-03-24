package pip.core

import twitter4j.{User, Status}
import javax.swing.ImageIcon
import actors.Futures.future
import pip.gui.{ImageService, NoServiceAvailable, TwitpicService, YfrogService}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */

case class Tweet(st: Status) {
  val status = if (st.isRetweet) st.getRetweetedStatus else st

  val user = status.getUser

  val id = status.getId
  val isFavorited = status.isFavorited
  val isRetweet = st.isRetweet
  val isRetweetedByMe = status.isRetweetedByMe

  val name = user.getName
  val nick = user.getScreenName
  val text = status.getText

  val retweetedBy = st.getUser.getScreenName
  val retweetCount = status.getRetweetCount

  val profileIcon = new ImageIcon(user.getProfileImageURL)

//  val urlList = status.getURLEntities match {
//    case null => Nil
//    case x => x.toList
//  }

  //getURLEntities zrejme nefunguje, docasna nahrazka:

  private val urlReg = """\w+://\S+""".r
  private val yfrogReg = """(http://yfrog.com/+.)""".r
  private val twitpicReg = """(http://twitpic.com/.+)""".r

  val urlList = urlReg findAllIn text toList

  val imagesList = urlList collect {
//  val imagesList: List[ImageService] = urlList collect {
    case yfrogReg(url) => new YfrogService(url)
    case twitpicReg(url) => new TwitpicService(url)
    case _ => NoServiceAvailable
  } filterNot { _ == NoServiceAvailable }

  def containsURLs = !urlList.isEmpty
  def containsImages = !imagesList.isEmpty
}