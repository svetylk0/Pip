package pip.gui

import swing._
import event._
import javax.swing.BorderFactory
import javax.swing.border.EmptyBorder
import java.awt.{Font, Color}
import pip.core.{URLShortener, Auth, PipCore, Loc}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 11.3.11
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */

class NewTweetWindow(pip: PipCore) extends MainFrame with URLShortener with DisposeOnClose {

  import Colors._

  val tweetSize = 140

  title = Loc("newTweet")
  minimumSize = new Dimension(400, 200)
  resizable = false

  object CloseButton extends Button {
    text = Loc("close")
  }

  object TweetButton extends Button {
    text = Loc("send")
  }

  object ShortenURLButton extends Button {
    text = Loc("shortenURL")
  }

  val counter = new Label {
    text = tweetSize.toString
    font = new Font("arial", Font.BOLD, 15)

    def update {
      val num = TweetText.text.size
      foreground = if (num <= tweetSize - 20) black else darkRed
      text = (tweetSize - num).toString

      //enabloavni/disablovani tweet buttonu
      TweetButton.enabled = if (num > tweetSize) false else true
    }
  }

  object TweetText extends TextArea {
    font = new Font("arial", Font.PLAIN, 20)
    border = BorderFactory.createLineBorder(Color.black)
    lineWrap = true
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += TweetText

      border = new EmptyBorder(10, 10, 5, 10)
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new FlowPanel(FlowPanel.Alignment.Left)(counter) {
        border = new EmptyBorder(0, 6, 0, 0)
      }
      contents += new FlowPanel(FlowPanel.Alignment.Right)(
        TweetButton,
        ShortenURLButton,
        CloseButton) {

        border = new EmptyBorder(0, 0, 0, 6)
      }
    }

  }

  listenTo(TweetText.keys, TweetButton, ShortenURLButton, CloseButton)

  reactions += {
    case ButtonClicked(TweetButton) =>
      pip.tweet(TweetText.text)
      dispose
    case ButtonClicked(CloseButton) => dispose
    case ButtonClicked(ShortenURLButton) =>
      TweetText.text = replaceURLs(TweetText.text)
      counter.update
    case KeyReleased(TweetText, _, _, _) => counter.update
  }

  //zobrazit az je vse inicializovano
  visible = true
}


/*testovani
object NewTweetWindow extends Application {
  val tw = Auth.authorizedTwitterInstance(Auth.loadAccessToken("myauth"))
  Loc.load("czech.loc")
  (new NewTweetWindow(new PipCore(tw))).visible = true
}*/
