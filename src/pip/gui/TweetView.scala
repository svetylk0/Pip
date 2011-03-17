package pip.gui

import java.awt.{Cursor, Graphics2D, LinearGradientPaint, Color, Insets}
import swing._
import scala.swing.event._
import pip.core.{Globals, Loc, Tweet}

class TweetView(tweet: Tweet) extends GridBagPanel {

  import Colors._
  import Globals._

  val iconLabel = new Label {
    icon = tweet.profileIcon
  }
  val userLabel = new Label {
    text = tweet.nick
  }
  val nameLabel = new Label {
    text = tweet.name
  }
  val tweetText = new TextArea(tweet.text, 3, 20) {
    editable = false
    lineWrap = true
    opaque = false
    preferredSize = size
    //wordWrap = true
  }

  val hand = new Cursor(Cursor.HAND_CURSOR)

  object FavoriteLabel extends Label {
    cursor = hand
    foreground = Color.blue
//    text = Loc("favorite")
    icon = favoriteIcon
  }

  object RetweetLabel extends Label {
    cursor = hand
    foreground = Color.blue
//    text = Loc("retweet")
    icon = retweetIcon
  }

  object ReplyLabel extends Label {
    cursor = hand
    foreground = Color.blue
//    text = Loc("reply")
    icon = replyIcon
  }

  val separator = new Separator

  val constraints = new Constraints
  constraints.anchor = GridBagPanel.Anchor.LineStart
  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridheight = 1 
  constraints.weightx = .5
  constraints.gridx = 0
  constraints.gridy = 1 
  constraints.insets = new Insets(0, 0, 0, 5) //top, left, bottom, right
  add(iconLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1 
  constraints.gridheight = 1
  constraints.gridx = 1
  constraints.gridy = 0
  constraints.insets = new Insets(0, 0, 0, 0)
  add(nameLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 2
  constraints.gridy = 0
  constraints.insets = new Insets(0, 0, 0, 0)
  add(userLabel, constraints)

  constraints.fill = GridBagPanel.Fill.None
  constraints.gridwidth = 4
  constraints.gridheight = 1
  constraints.gridx = 1
  constraints.gridy = 1
  constraints.insets = new Insets(0, 0, 0, 0)
  add(tweetText, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 1
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
  add(FavoriteLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 2
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
  add(RetweetLabel, constraints)
 
  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 3
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
  add(ReplyLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 4
  constraints.gridheight = 1
  constraints.gridx = 0
  constraints.gridy = 3
  constraints.insets = new Insets(0, 0, 0, 0)
  add(separator, constraints)

  //border = Swing.EmptyBorder(10, 10, 10, 10)
  background = Color.white
  val width = iconLabel.size.width + tweetText.size.width
  val height = userLabel.size.height + tweetText.size.height + 
               FavoriteLabel.size.height + separator.size.height
  minimumSize = new Dimension(width, height)

  listenTo(mouse.moves, tweetText.mouse.moves, ReplyLabel.mouse.moves,
           RetweetLabel.mouse.moves, FavoriteLabel.mouse.moves)

  reactions += {
    case e: MouseEntered =>
      //osetrit higlight labelu
      e.source match {
        case ReplyLabel => ReplyLabel.icon = replyHighlightIcon
        case RetweetLabel => RetweetLabel.icon = retweetHighlightIcon
        case FavoriteLabel => FavoriteLabel.icon = favoriteHighlightIcon
        case _ =>
      }

      background = lightGray
    case e: MouseExited => 
      //osetrit higlight labelu
      e.source match {
        case ReplyLabel => ReplyLabel.icon = replyIcon
        case RetweetLabel => RetweetLabel.icon = retweetIcon
        case FavoriteLabel => FavoriteLabel.icon = favoriteIcon
        case _ =>
      }

      background = white
  }

  /*override def paintComponent(g: Graphics2D) {
    val gradientHeight = size.getHeight.toInt
    val gradientWidth = size.getWidth.toInt
    val gradient = new LinearGradientPaint(
      0, 0, 0, size.getHeight.toFloat,
      Array[Float](.0f, .499f, .5f, 1.f),
      Array[Color](
        new Color(0xFFFFFF),
        new Color(0xC0C0C0),
        new Color(0xC0C0C0),
        new Color(0xFFFFFF)))

    val paint = g.getPaint
    g.setPaint(gradient)
    g.fillRect(0, 0, gradientWidth, gradientHeight)
    g.setPaint(paint)
    //super.paintComponent(g)
  }*/
}
