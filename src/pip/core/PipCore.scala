package pip.core

import actors.Futures.future
import twitter4j.{StatusUpdate, Query, Paging, Twitter}
import collection.JavaConversions._

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

  private def mentionsPage(pageNum: Int, tweetCount: Int = defaultTweetCount) =
    tw.getMentions(new Paging(pageNum, tweetCount)).toList

  def favorite(id: Long) = tw.createFavorite(id)

  def unFavorite(id: Long) = tw.destroyFavorite(id)

  def mentions(): List[Tweet] = mentions(defaultTweetCount)

  def mentions(tweetCount: Int, page: Int = 1) = mentionsPage(page, tweetCount) map Tweet

//  def mentionsFutures: List[Future[Tweet]] = mentionsFutures(defaultTweetCount)

  def mentionsFutures(tweetCount: Int, page: Int = 1) = mentionsPage(page, tweetCount) map {
    status => future {
      Tweet(status)
    }
  }

  def homeTimeline(): List[Tweet] = homeTimeline(defaultTweetCount)

  def homeTimeline(tweetCount: Int, page: Int = 1) = homeTimelinePage(page, tweetCount) map Tweet

//  def homeTimelineFutures(): List[Future[Tweet]] = homeTimelineFutures(defaultTweetCount)

  def homeTimelineFutures(tweetCount: Int, page: Int = 1) = homeTimelinePage(page, tweetCount) map {
    status => future {
      Tweet(status)
    }
  }

  def tweet(text: String) = tw.updateStatus(text)

  def tweet(text: String, inReplyToId: Long) = {
    val st = new StatusUpdate(text)
    st.setInReplyToStatusId(inReplyToId)
    tw.updateStatus(st)
  }

  def retweet(id: Long) = tw.retweetStatus(id)

  def undoRetweet(id: Long) = tw.destroyStatus(id)

  def search(query: String) = tw.search(new Query(query)).getTweets.toList map {
    tw4jTweet =>
      Tweet(tw.showStatus(tw4jTweet.getId))
  }

  def searchAsFutures(query: String) = tw.search(new Query(query)).getTweets.toList map {
    tw4jTweet => future {
      Tweet(tw.showStatus(tw4jTweet.getId))
    }
  }

  def addSavedSearch(query: String) = tw.createSavedSearch(query)

  def removeSavedSearch(id: Int) = tw.destroySavedSearch(id)
  
  def savedSearch = tw.getSavedSearches.toList
  
  def savedSearchId(key: String) = savedSearch find { _.getName == key } match {
    case Some(x) => x.getId
    case None => -1
  }
  
  def sendDirectMessage(to: String, text: String) = tw.sendDirectMessage(to,text)

  def sendDirectMessage(id: Long, text: String) = tw.sendDirectMessage(id,text)

  def directMessages() = tw.getDirectMessages.toList

  def trends() = tw.getTrends.getTrends.toList

  def screenName = tw.getScreenName
}