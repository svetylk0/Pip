package pip.core

import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */

object Auth {

  val consumerKey="bWYDg4gHUN8bcdSuwo0OQ"
  val consumerSecret="qDSpvrfEy6MVUm0FsyiL1GLG8JLlJ119KecVcDp27k4"

  private val instance = plainTwitterInstance
  private val requestToken = instance.getOAuthRequestToken

  private def plainTwitterInstance = {
    val tw = (new TwitterFactory).getInstance
    tw.setOAuthConsumer(consumerKey, consumerSecret)
    tw
  }

  def authURL = requestToken.getAuthorizationURL

  def tokenStringAndSecret(pin: String) = {
    val accessToken = instance.getOAuthAccessToken(requestToken, pin)
    (accessToken.getToken, accessToken.getTokenSecret)
  }

  def authorizedTwitterInstance(token: String, secret: String) = {
    instance.setOAuthAccessToken(new AccessToken(token,secret))
    instance
  }

}