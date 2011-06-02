package pip.net

import java.io.OutputStreamWriter
import java.net.{URLConnection, URL}
import pip.core.Implicits

/**
 * Created by IntelliJ IDEA.
 * Author: svetylk0@seznam.cz
 * Date: 31.1.11
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */

class Http(userAgent: String,
           encoding: String,
           HttpRequestTimeout: Int = 15000) {

  import collection.JavaConversions._
  import Implicits.wrapInputStream
  import java.net.URLEncoder.encode

  var cookies = Map[String, String]()

  private def loadCookies(conn: URLConnection) {
    for ((name, value) <- cookies) conn.setRequestProperty("Cookie", name + "=" + value)
  }

  private def saveCookies(conn: URLConnection) {
    conn.getHeaderFields.lift("Set-Cookie") match {
      case Some(cList) => cList foreach { c =>
        val (name,value) = c span { _ != '=' }
        cookies += name -> (value drop 1)
      }
      case None =>
    }
  }

  private def encodePostData(data: Map[String, String]) =
    (for ((name, value) <- data) yield encode(name, encoding) + "=" + encode(value, encoding)).mkString("&")


  private def setConnectionProperties(c: URLConnection) {
    c.setRequestProperty("User-Agent", userAgent)
    c.setRequestProperty("Cache-Control", "max-age=0")
    c.setRequestProperty("Accept","application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5")
    c.setRequestProperty("Accept-Encoding","gzip,deflate,sdch")
    c.setRequestProperty("Accept-Charset","windows-1250,utf-8;q=0.7,*;q=0.3")
    c.setConnectTimeout(HttpRequestTimeout)
  }

  def Get(url: String) = {
    val u = new URL(url)
    val conn = u.openConnection()

    setConnectionProperties(conn)

    //nacist cookies do URLConnection
    loadCookies(conn)

    conn.connect

    //ulozit cookies
    saveCookies(conn)

    //vratit InputStream jako String
    conn.getInputStream.mkString
  }

  def Post(url: String, data: Map[String, String]) = {
    val u = new URL(url)
    val conn = u.openConnection

    setConnectionProperties(conn)

    //nacist cookies do URLConnection
    loadCookies(conn)

    //ulozit data pro POST
    conn.setDoOutput(true)
    val wr = new OutputStreamWriter(conn.getOutputStream())
    wr.write(encodePostData(data))
    wr.flush
    wr.close

    conn.connect

    //ulozit cookies
    saveCookies(conn)

    //vratit InputStream jako String
    conn.getInputStream.mkString
  }
}
