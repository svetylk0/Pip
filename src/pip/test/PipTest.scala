package pip.test

import pip.core._
import pip.gui.TweetView
import swing.{BoxPanel, Orientation, TabbedPane, MainFrame}

import Implicits._



/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 26.2.11
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */

object PipTest extends App {
//  println("Otevri tuto URL v prohlizeci:")
//  println(Auth.authURL)
//  println("a zadej pin:")
//  val pin = readLine

//  val (t,s) = Auth.tokenStringAndSecret(pin)
//  val tw = Auth.authorizedTwitterInstance(t,s)

//  Auth.saveAccessToken(t,s,"myauth")
  val tw = Auth.authorizedTwitterInstance(Auth.loadAccessToken("myauth"))
  val core = new PipCore(tw)

//  println("Je Twitter.com dostupny? " + {
//    if (Tools.isConnectionAvailable) "ano" else "ne"
//  })

  Loc.load("czech.loc")
  println("Test lokalizace: "+Loc("welcome"))


//  println("Pager Test:")
  val pager = new TweetPager(2) {
    val f = core.homeTimelineFutures _
  }

  val testFrame = new MainFrame {
    contents = new TweetView(pager.firstPage.head())
  }


  val tpanel = new BoxPanel(Orientation.Vertical) {
    contents ++= pager.firstPage
  }

  val tpanel2 = new BoxPanel(Orientation.Vertical) {
    contents ++= pager.firstPage
  }

  val testFrame2 = new MainFrame {
    contents = new TabbedPane {
      pages += new TabbedPane.Page(Loc("tweets"), tpanel)
      pages += new TabbedPane.Page("tweets", tpanel2)
    }
  }

//  testFrame.visible = true
  testFrame2.visible = true

//  println("First page:")
//  pager.firstPage foreach {
//    x => println(x.name+": "+x.text)
//  }

//  println("Next page:")
//  pager.nextPage foreach {
//    x => println(x.name+": "+x.text)
//  }

//  println("Previous page:")
//  pager.previousPage foreach {
//    x => println(x.name+": "+x.text)
//  }

//  println("Previous page:")
//  pager.previousPage foreach {
//    x => println(x.name+": "+x.text)
//  }

//  println("\nHome timeline (using futures):")
//  core.homeTimelineFutures foreach {
//    x => println(x().name+": "+x().text)
//  }

  //test tweetnuti
//  core.tweet("test")


}
