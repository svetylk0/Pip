package pip.core

import java.net.URL
import java.io.File
import javax.swing.ImageIcon
import concurrent.ThreadRunner
import java.io.InputStream
import java.io.OutputStream
import java.io.FileOutputStream

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

  def cacheIconFromURL(url: URL, file: String) {
    transferStream(url.openStream, 
                   new FileOutputStream(imagesCacheDir+File.separator+file))
  }
  
  def loadCachedImageIcon(file: String) = 
    loadIcon(imagesCacheDir+File.separator+file)
  
  def transferStream(in: InputStream, out: OutputStream) {
    val buf = new Array[Byte](5000)
    Iterator.continually(in.read(buf)) takeWhile ( _ != -1 ) foreach {
      out.write(buf,0,_)
    }
    in.close
    out.close
  }
  
  def isConnectionAvailable = {
    try {
      (new URL("http://www.twitter.com/")).openConnection.connect
      true
    } catch {
      case _ => false
    }
  }

  def mkDir(dirName: String) {
    val f = new File(dirName)
    if (!f.exists) f.mkdir
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

  private val tr = new ThreadRunner
  def thread(f: => Unit) = tr.execute(f _)

}