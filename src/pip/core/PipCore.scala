package pip.core

import collection.JavaConversions._
import actors.Futures.future
import actors.Future
import twitter4j.{Query, Paging, Twitter}

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

  def mentions = tw.getMentions.toList map Tweet

  def mentionsFutures = tw.getMentions.toList map {
    status => future {
      Tweet(status)
    }
  }

  def homeTimeline: List[Tweet] = homeTimeline(defaultTweetCount)

  def homeTimeline(tweetCount: Int) = homeTimelinePage(1, tweetCount) map Tweet

  def homeTimelineFutures: List[Future[Tweet]] = homeTimelineFutures(defaultTweetCount)

  def homeTimelineFutures(tweetCount: Int) = homeTimelinePage(1, tweetCount) map {
    status => future {
      Tweet(status)
    }
  }

  def tweet(text: String) = tw.updateStatus(text)

  def search(query: String) = tw.search(new Query(query)).getTweets.toList map {
    tw4jTweet =>
      Tweet(tw.showStatus(tw4jTweet.getId))
  }

  def searchAsFutures(query: String) = tw.search(new Query(query)).getTweets.toList map {
    tw4jTweet => future {
      Tweet(tw.showStatus(tw4jTweet.getId))
    }
  }

  def sendDirectMessage(to: String, text: String) = tw.sendDirectMessage(to,text)

  def sendDirectMessage(id: Long, text: String) = tw.sendDirectMessage(id,text)

  def directMessages = tw.getDirectMessages.toList

  def trends = tw.getTrends.getTrends.toList


}