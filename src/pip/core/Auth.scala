package pip.core

import twitter4j.auth.AccessToken
import java.util.Properties
import java.io.{FileInputStream, FileOutputStream}
import twitter4j.{Twitter, TwitterFactory}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */

object Auth {
  import Globals._

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

  def authorizedTwitterInstance(at: AccessToken) = {
    instance.setOAuthAccessToken(at)
    instance
  }

  def authorizedTwitterInstance(token: String, secret: String): Twitter =
    authorizedTwitterInstance(new AccessToken(token,secret))

  def loadAccessToken(file: String) = {
    val prop = new Properties
    prop.load(new FileInputStream(file))
    new AccessToken(prop.getProperty("token"),prop.getProperty("secret"))
  }

  def saveAccessToken(token: String, secret: String, file: String) {
    val prop = new Properties
    prop.setProperty("token", token)
    prop.setProperty("secret", secret)
    prop.store(new FileOutputStream(file),"Access token")
  }

  def saveAccessToken(at: AccessToken, file: String): Unit =
    saveAccessToken(at.getToken, at.getTokenSecret, file)
}