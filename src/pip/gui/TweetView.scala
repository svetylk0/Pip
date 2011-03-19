package pip.gui

import java.awt.{Cursor, Graphics2D, LinearGradientPaint, Color, Insets}
import swing._
import scala.swing.event._
import pip.core.{Tools, Globals, Loc, Tweet}

class TweetView(tweet: Tweet) extends GridBagPanel {

  import Colors._
  import Globals._
  import Tools._
  import MainWindow.{core,reloadMentionsPanel,reloadTweetsPanel}

  val iconLabel = new Label {
    icon = tweet.profileIcon
  }
  val userLabel = new Label {
    text = tweet.nick
  }
  val nameLabel = new Label {
    text = tweet.name
  }
  val tweetText = new TextArea(tweet.text, 3, 40) {
    editable = false
    font = userLabel.font
    lineWrap = true
    opaque = false
    wordWrap = true
  }

  val hand = new Cursor(Cursor.HAND_CURSOR)

  object FavoriteLabel extends Label with LabelIconHighlighting {
    val defaultIcon = if (tweet.isFavorited) favoriteHighlightIcon2 else favoriteIcon
    val highLightIcon = if (tweet.isFavorited) favoriteIcon else favoriteHighlightIcon

    tooltip = Loc("favorite")
    cursor = hand
    icon = defaultIcon
  }

  object RetweetLabel extends Label with LabelIconHighlighting {
    val defaultIcon = if (tweet.isRetweetedByMe) retweetHighlightIcon2 else retweetIcon
    val highLightIcon = if (tweet.isRetweetedByMe) retweetIcon else retweetHighlightIcon

    tooltip = Loc("retweet")
    cursor = hand
    icon = defaultIcon
  }

  object ReplyLabel extends Label with LabelIconHighlighting {
    val defaultIcon = replyIcon
    val highLightIcon = replyHighlightIcon

    tooltip = Loc("reply")
    cursor = hand
    icon = defaultIcon
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

 /* constraints.fill = GridBagPanel.Fill.Horizontal
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
  add(RetweetLabel, constraints) */
 
  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 3
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
//  add(ReplyLabel, constraints)
  add(new FlowPanel(FlowPanel.Alignment.Right)(FavoriteLabel, RetweetLabel, ReplyLabel) {
    background = transparent
  }, constraints)

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

  listenTo(mouse.moves,
           mouse.clicks,
           tweetText.mouse.moves,
           tweetText.mouse.clicks,
           ReplyLabel.mouse.moves,
           ReplyLabel.mouse.clicks,
           RetweetLabel.mouse.moves,
           RetweetLabel.mouse.clicks,
           FavoriteLabel.mouse.moves,
           FavoriteLabel.mouse.clicks)

  reactions += {
    case MouseClicked(RetweetLabel,_,_,_,_) =>
      if (tweet.isRetweetedByMe) core.undoRetweet(tweet.id) else core.retweet(tweet.id)
      MainWindow.reloadActiveTabPanel()

    case MouseClicked(FavoriteLabel,_,_,_,_) =>
      if (tweet.isFavorited) core.unFavorite(tweet.id) else core.favorite(tweet.id)
      MainWindow.reloadActiveTabPanel()

    case MouseClicked(ReplyLabel,_,_,_,_) =>
      new NewTweetWindow(core, this, tweet)

    case e: MouseEntered =>
      //osetrit higlight labelu
      e.source match {
        case ReplyLabel => ReplyLabel.highLight()
        case RetweetLabel => RetweetLabel.highLight()
        case FavoriteLabel => FavoriteLabel.highLight()
        case _ =>
      }

      background = tweetHighlightBlue
    case e: MouseExited => 
      //osetrit higlight labelu
      e.source match {
        case ReplyLabel => ReplyLabel.deHighlight()
        case RetweetLabel => RetweetLabel.deHighlight()
        case FavoriteLabel => FavoriteLabel.deHighlight()
        case _ =>
      }

      background = white
      //resi chybne vykreslovani reply ikonky po najeti na jiny tweet
      MainWindow.mainFrame.repaint

    case e: MouseClicked =>
      e.modifiers match {
        case `leftMouseButton` => openTweetInBrowser(tweet)
        case _ =>
      }

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
