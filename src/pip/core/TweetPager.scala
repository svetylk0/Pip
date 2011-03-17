package pip.core

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 15.3.11
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */

class TweetPager[A](tweetsPerPage: Int, f: (Int,Int) => List[A]) {
  var page = 1

  private def getPage = f(tweetsPerPage,page)

  def firstPage() = {
    page = 1
    getPage
  }

  def nextPage() = {
    page += 1
    getPage
  }

  def previousPage() = {
    page = if (page > 1) page-1 else page
    getPage
  }
}