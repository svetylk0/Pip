package pip.test

import pip.core._

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

  Auth.saveAccessToken(t,s,"myauth")
  //val tw = Auth.authorizedTwitterInstance(Auth.loadAccessToken("myauth"))
  val core = new PipCore(tw)

  println("Je Twitter.com dostupny? " + {
    if (Tools.isConnectionAvailable) "ano" else "ne"
  })

  Loc.load("czech.loc")
  println("Test lokalizace: "+Loc("welcome"))


  println("Home timeline:")
  core.homeTimeline foreach {
    x => println(x.name+": "+x.text)
  }


//  println("\nHome timeline (using futures):")
//  core.homeTimelineFutures foreach {
//    x => println(x().name+": "+x().text)
//  }

  //test tweetnuti
//  core.tweet("test")


}
