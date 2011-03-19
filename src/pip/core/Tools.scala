package pip.core

import java.net.URL
import java.io.File
import javax.swing.ImageIcon

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 27.2.11
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */

object Tools {

  import Globals._

  def fileExists(file: String) = new File(file) exists

  def loadIcon(file: String) = new ImageIcon(resourcesDir+File.separator+file)

  def isConnectionAvailable = {
    try {
      (new URL("http://www.twitter.com/")).openConnection.connect
      true
    } catch {
      case _ => false
    }
  }

  def waitUntil(cond: => Boolean, timeout: Long = 100l) {
    while (cond) {
      Thread.sleep(timeout)
    }
  }

  def languagesList = (new File(localizationDir)).list filter {
    _.endsWith(".loc")
  } sorted

  def openURLInBrowser(url: String) {
    Runtime.getRuntime.exec(browserCommand.replace("%url",url))
  }

  def openTweetInBrowser(tweet: Tweet) {
    openURLInBrowser("http://twitter.com/#!/"+tweet.nick+"/status/"+tweet.id)
  }
}