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

  def Get(url: String) = {
    val u = new URL(url)
    val conn = u.openConnection()
    conn.setRequestProperty("User-Agent", userAgent)
    conn.setConnectTimeout(HttpRequestTimeout)

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

    conn.setRequestProperty("User-Agent", userAgent)
    conn.setConnectTimeout(HttpRequestTimeout)

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