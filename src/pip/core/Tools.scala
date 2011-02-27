package pip.core

import java.net.URL

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 27.2.11
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */

object Tools {
  def isConnectionAvailable = {
    try {
      (new URL("http://www.twitter.com/")).openConnection.connect
      true
    } catch {
      case _ => false
    }
  }


}