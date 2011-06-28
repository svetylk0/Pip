package pip.core

import actors.Future

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 15.3.11
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */

abstract class TweetPager(tweetsPerPage: Int) {
  val f: (Int,Int) => List[Tweet]

  private def getPage() = f(tweetsPerPage,page)

  var page = 1

  def currentPage() = getPage()

  def firstPage() = {
    page = 1
    getPage()
  }

  def nextPage() = {
    page += 1
    getPage()
  }

  def previousPage() = {
    page = if (page > 1) page-1 else page
    getPage()
  }
}

class TweetListPager(tweetsPerPage: Int) extends TweetPager(tweetsPerPage) {
  var tweetList = List[Tweet]()

  val f = (tweetsPerPage: Int, pageNum: Int) => {
    tweetList.sliding(tweetsPerPage,tweetsPerPage).toList.lift(pageNum) match {
      case Some(x) => x
      case None => List[Tweet]()
    }
  }
}