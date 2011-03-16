package pip.core

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 16.3.11
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

object Implicits {
  //rozsireni pro uzavirani stringu do HTML tagu
  implicit def encloseStringInHtmlTag(s: String) = new {
    def tagA = "<a href=\""+s+"\">"+s+"</a>"
    def tagHtml = "<html>"+s+"</html>"
  }
}