package pip.gui

import swing._
import scala.swing.event._
import actors.Actor.actor
import pip.core._
import javax.swing.border.EmptyBorder
import java.awt.{Font, Cursor, Color, Insets}

class TweetView(tweet: Tweet) extends GridBagPanel {

  import Colors._
  import Globals._
  import Tools._
  import MainWindow.core

  class TransparentFlowPanel extends FlowPanel {
    background = transparent
  }

  val iconLabel = new Label {
    icon = tweet.profileIcon
  }

//  val userLabel = new Label {
//    text = tweet.nick
//  }

  //docasne reseni, jak zobrazit, kym je tweet retweetnuty
  val userLabel = new BoxPanel(Orientation.Horizontal) with TransparentBackgroundComponent {
    contents += new Label {
      text = tweet.nick
    }

    if (tweet.isRetweet) contents += new Label {
      text = "by "+tweet.retweetedBy
      icon = retweetIcon
      border = new EmptyBorder(0,5,0,0)
    }
  }

  val nameLabel = new Label {
    text = tweet.name
  }
  val tweetText = new TextArea(tweet.text, 3, 40) {
    editable = false
//    font = userLabel.font
    font = font.deriveFont(Font.BOLD, tweetFontSize)
    lineWrap = true
    opaque = false
    wordWrap = true
  }

  val hand = new Cursor(Cursor.HAND_CURSOR)

  object FavoriteLabel extends HighlightableLabel {
    val defaultIcon = if (tweet.isFavorited) favoriteHighlightIcon2 else favoriteIcon
    val highLightIcon = if (tweet.isFavorited) favoriteIcon else favoriteHighlightIcon
    val defaultText = Loc("favorite")

    cursor = hand
    icon = defaultIcon

    //je treba zavolat, aby se nacetl text
    deHighlight()
  }

  object RetweetLabel extends HighlightableLabel {
    val defaultIcon = if (tweet.isRetweetedByMe) retweetHighlightIcon2 else retweetIcon
    val highLightIcon = if (tweet.isRetweetedByMe) retweetIcon else retweetHighlightIcon
    val defaultText = Loc("retweet")

    cursor = hand
    icon = defaultIcon

    //je treba zavolat, aby se nacetl text
    deHighlight()
  }

  object ReplyLabel extends HighlightableLabel {
    val defaultIcon = replyIcon
    val highLightIcon = replyHighlightIcon
    val defaultText = Loc("reply")

    cursor = hand
    icon = defaultIcon

    //je treba zavolat, aby se nacetl text
    deHighlight()
  }

  val URLMenu = new URLMenu {
    focusable = false
    opaque = false
    contents += new Menu("") {
      focusable = false 
      opaque = false
      contents += new MenuItem("url 1")
      contents += new MenuItem("url 2")
      rolloverEnabled = true
      cursor = hand
      icon = urlIcon
      rolloverIcon = urlHighlightIcon
      tooltip = Loc("openURL")
    }
  }

  object ImageLabel extends HighlightableLabel {
    val defaultIcon = imageIcon
    val highLightIcon = imageHighlightIcon
    val defaultText = ""

    tooltip = Loc("showImages")
    cursor = hand
    icon = defaultIcon
  }

  object URLLabel extends HighlightableLabel {
    val defaultIcon = urlIcon
    val highLightIcon = urlHighlightIcon
    val defaultText = ""

    tooltip = Loc("openURL")
    cursor = hand
    icon = defaultIcon
  }

  object IconsFlowPanel extends FlowPanel(FlowPanel.Alignment.Left)() with TransparentBackgroundComponent {
    contents += FavoriteLabel
    contents += RetweetLabel
    contents += ReplyLabel
    opaque = false
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
  add(userLabel, constraints)

  constraints.fill = GridBagPanel.Fill.Horizontal
  constraints.gridwidth = 1
  constraints.gridheight = 1
  constraints.gridx = 2
  constraints.gridy = 0
  constraints.insets = new Insets(0, 0, 0, 0)
  add(nameLabel, constraints)

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
  constraints.gridx = 1
  constraints.gridy = 2
  constraints.insets = new Insets(0, 0, 0, 0)
  add(IconsFlowPanel, constraints)

  //na tohle se bude nejspise vice hodit mutable kolekce
  import collection.mutable
  val componentList = new mutable.ListBuffer[Component]()
  if (tweet.containsImages) componentList += ImageLabel
  if (tweet.containsURLs) componentList += URLMenu

  //pokud je URL ve tweetu, pridat prislusnou komponentu
  if (tweet.containsURLs) {
    constraints.fill = GridBagPanel.Fill.Horizontal
    constraints.gridwidth = 1
    constraints.gridheight = 1
    constraints.gridx = 3
    constraints.gridy = 2
    constraints.insets = new Insets(0, 0, 0, 0)
    add(new FlowPanel(FlowPanel.Alignment.Right)(componentList: _*) with TransparentBackgroundComponent, constraints)
//    add(URLMenu, constraints)
  }

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
           FavoriteLabel.mouse.clicks,
           URLLabel.mouse.moves,
           URLLabel.mouse.clicks,
           ImageLabel.mouse.moves,
           ImageLabel.mouse.clicks)

  reactions += {
    case MouseClicked(RetweetLabel,_,_,_,_) =>
      if (tweet.isRetweetedByMe) core.undoRetweet(tweet.id) else core.retweet(tweet.id)
      MainWindow.refreshActiveTab

    case MouseClicked(FavoriteLabel,_,_,_,_) =>
      if (tweet.isFavorited) core.unFavorite(tweet.id) else core.favorite(tweet.id)
      MainWindow.refreshActiveTab

    case MouseClicked(ReplyLabel,_,_,_,_) =>
      new NewTweetWindow(core, this, tweet)

    case MouseClicked(URLLabel,_,_,_,_) =>

    case MouseClicked(ImageLabel,_,_,_,_) =>
      actor {
        val notif = Notifications.simpleNotification(Loc("loadingImages"),ImageLabel)
        try {
          tweet.imagesList foreach { img =>
            new ImageView(img.getIcon)
          }
        } catch {
          case e: Exception => e.printStackTrace
        } finally {
          notif.dispose
        }
      }

    case e: MouseEntered =>
      //osetrit higlight labelu
      e.source match {
        case x: HighlightableLabel => x.highLight()
        case _ =>
      }

      background = tweetHighlightBlue
    case e: MouseExited =>
      //osetrit higlight labelu
      e.source match {
        case x: HighlightableLabel => x.deHighlight
        case _ =>
      }

      background = white
      //resi chybne vykreslovani reply ikonky po najeti na jiny tweet
      MainWindow.repaint()
      ReplyLabel.repaint()

    case e: MouseClicked =>
      e.modifiers match {
        case `leftMouseButton` =>
          actor {
            Animations.backgroundColorTransition(specialBlue,white,this,MainWindow.mainFrame)
            openTweetInBrowser(tweet)
          }
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
