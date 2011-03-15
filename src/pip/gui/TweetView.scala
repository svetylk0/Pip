package pip.gui

import pip.core.Tweet
import java.awt.{Graphics2D, LinearGradientPaint, Color, Insets}
import swing._

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
    wordWrap = true
  }

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
  constraints.gridheight = 1
  constraints.gridx = 1
  constraints.gridy = 0
  constraints.insets = new Insets(0, 0, 0, 0)
  add(nameLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridheight = 1
  constraints.gridx = 2
  constraints.gridy = 0
  constraints.insets = new Insets(0, 0, 0, 0)
  add(userLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 2
  constraints.gridheight = 1
  constraints.gridx = 1
  constraints.gridy = 1
  constraints.insets = new Insets(0, 0, 0, 0)
  add(tweetText, constraints)

  border = Swing.EmptyBorder(10, 10, 10, 10)

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
