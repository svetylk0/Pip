package pip.gui

import swing._
import event._
import pip.core.{Tools, Config, Loc}
import javax.swing.GroupLayout.Alignment

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 12.3.11
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */

class PreferencesWindow(parent: UIElement = null) extends MainFrame with DisposeOnClose {
  title = Loc("preferences")

  def rowItemLeft(c: Component*) = new FlowPanel(FlowPanel.Alignment.Left)(c: _*) {
    val rowHeight = (c map (_.preferredSize.getHeight) max).toInt
    val space = 10
    maximumSize = new Dimension(Int.MaxValue,rowHeight+space)
  }

  private val languages = Tools.languagesList

  val language = new ComboBox(languages) {
    selection.index = languages indexOf Config("language")
  }

  val languageItem = rowItemLeft(new Label {
    text = Loc("localizationFile")+":"
  }, language)


  object ApplyButton extends Button {
    text = Loc("apply")
  }

  object SaveButton extends Button {
    text = Loc("save")
  }

  object CloseButton extends Button {
    text = Loc("close")
  }

  val a = new Button("A")


  val languagePanel = new BoxPanel(Orientation.Vertical) {
    contents += rowItemLeft(new Label("Jazyk:"))
    contents += languageItem
  }

  contents = new BorderPanel() {
    val tab = new TabbedPane {
      pages += new TabbedPane.Page(Loc("language"), languagePanel)
    }

    add(tab, BorderPanel.Position.Center)
    add(new FlowPanel(FlowPanel.Alignment.Right)(SaveButton, ApplyButton, CloseButton),
        BorderPanel.Position.South)

  }

  listenTo(SaveButton, ApplyButton, CloseButton)

  def savePreferences {
    Config("language") = languages(language.selection.index)

    Config.saveConfig
  }

  reactions += {
    case ButtonClicked(ApplyButton) => savePreferences
    case ButtonClicked(SaveButton) =>
      savePreferences
      dispose
    case ButtonClicked(CloseButton) => dispose
  }

  minimumSize = new Dimension(600,400)
  if (parent == null) peer.setLocationRelativeTo(null) else setLocationRelativeTo(parent)
}

object PreferencesWindow {
  Loc.load("czech.loc")
  Config.loadConfig

  def show {
    (new PreferencesWindow).visible = true
  }

  def main(args: Array[String]) {
    show
  }
}