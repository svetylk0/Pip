package pip.core

import collection.JavaConversions._
import twitter4j.{Paging, Twitter}
import actors.Futures.future
import actors.Future

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 27.2.11
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */

class PipCore(tw: Twitter) {
  import Globals._

  private def homeTimelinePage(pageNum: Int, tweetCount: Int = defaultTweetCount) =
    tw.getHomeTimeline(new Paging(pageNum, tweetCount)).toList


  def homeTimeline: List[Tweet] = homeTimeline(defaultTweetCount)

  def homeTimeline(tweetCount: Int) = homeTimelinePage(1, tweetCount) map Tweet

  def homeTimelineFutures: List[Future[Tweet]] = homeTimelineFutures(defaultTweetCount)

  def homeTimelineFutures(tweetCount: Int) = homeTimelinePage(1, tweetCount) map {
    status => future {
      Tweet(status)
    }
  }

}