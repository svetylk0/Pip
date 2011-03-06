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
  val id = status.getId
  val user = status.getUser

  val name = user.getScreenName
  val nick = user.getName
  val text = status.getText

  val profileIcon = new ImageIcon(user.getProfileImageURL)

  val urlList = status.getURLEntities match {
    case null => None
    case x => Some(x.toList)
  }
}