package pip.gui

import pip.core.{Loc, Tweet}
import java.awt.{Cursor, Graphics2D, LinearGradientPaint, Color, Insets}
import swing._
import scala.swing.event._

class TweetView(tweet: Tweet) extends GridBagPanel {

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
    wordWrap = true
  }

  val hand = new Cursor(Cursor.HAND_CURSOR)

  val favoriteLabel = new Label {
    cursor = hand
    foreground = Color.blue
    text = Loc("favorite")
    visible = false
  }

  val retweetLabel = new Label {
    cursor = hand
    foreground = Color.blue
    text = Loc("retweet")
    visible = false
  }

  val replyLabel = new Label {
    cursor = hand
    foreground = Color.blue
    text = Loc("reply")
    visible = false
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

  constraints.fill = GridBagPanel.Fill.Horizontal
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
  add(favoriteLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 2
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
  add(retweetLabel, constraints)
 
  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 3
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
  add(replyLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 4
  constraints.gridheight = 1
  constraints.gridx = 0
  constraints.gridy = 3
  constraints.insets = new Insets(0, 0, 0, 0)
  add(separator, constraints)

  border = Swing.EmptyBorder(10, 10, 10, 10)
  background = Color.white
  val width = iconLabel.size.width + tweetText.size.width
  val height = userLabel.size.height + tweetText.size.height + 
               favoriteLabel.size.height + separator.size.height
  minimumSize = new Dimension(width, height)

  listenTo(mouse.moves, `tweetText`)
  reactions += {
    case e: MouseEntered => 
      background = Color.gray
      favoriteLabel.visible = true
      retweetLabel.visible = true
      replyLabel.visible = true
    case e: MouseExited => 
      background = Color.white
      favoriteLabel.visible = false
      retweetLabel.visible = false
      replyLabel.visible = false
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
