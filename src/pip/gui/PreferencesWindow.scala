package pip.gui

import swing._
import event._
import pip.core.{Tools, Config, Loc}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 12.3.11
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */

class PreferencesWindow extends MainFrame with DisposeOnClose {
  title = Loc("preferences")

  def optionItem(c: Component*) = new FlowPanel(FlowPanel.Alignment.Center)(c: _*)

  private val languages = Tools.languagesList

  val language = new ComboBox(languages) {
    selection.index = languages indexOf Config("language")
  }

  val languageItem = optionItem(new Label {
    text = Loc("language")+":"
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

  contents = new BoxPanel(Orientation.Vertical) {
    contents += languageItem
    contents += optionItem(SaveButton, ApplyButton, CloseButton)
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