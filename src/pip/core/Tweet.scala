package pip.core

import twitter4j.{User, Status}
import javax.swing.ImageIcon
import actors.Futures.future

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */

case class Tweet(status: Status) {
  val user = status.getUser

  val id = status.getId
  val isFavorited = status.isFavorited
  val isRetweetedByMe = status.isRetweetedByMe
  val name = user.getName
  val nick = user.getScreenName
  val text = status.getText
  val retweetCount = status.getRetweetCount

  val profileIcon = new ImageIcon(user.getProfileImageURL)

  val urlList = status.getURLEntities match {
    case null => None
    case x => Some(x.toList)
  }
}