package pip.test

import pip.core._
import collection.JavaConversions._
import twitter4j.TwitterFactory

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */

object PipTest extends Application {
  println("Otevri tuto URL v prohlizeci:")
  println(Auth.authURL)
  println("a zadej pin:")
  val pin = readLine

  val (t,s) = Auth.tokenStringAndSecret(pin)
  val tw = Auth.authorizedTwitterInstance(t,s)

  println("Home timeline:")
  tw.getHomeTimeline foreach { x =>
    println(x.getUser.getName+": "+x.getText)
  }

}