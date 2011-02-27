package pip.gui

import pip.core.Tweet
import scala.swing.{SimpleSwingApplication, MainFrame, Label, GridBagPanel, Swing}


object TweetView extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Second Swing Application"
    val iconLabel = new Label {
      text = "Ikonka"
    }
    val userLabel = new Label {
      text = "Uzivatel"
    }
    val nameLabel = new Label {
      text = "Jmeno"
    }
    val textLabel = new Label {
      text = "Cvrlikani"
    }
    
    contents = new GridBagPanel {
      //add(iconLabel, new Constraints)
      val constraints = new Constraints
      constraints.fill = GridBagPanel.Fill.Horizontal
      constraints.gridx = 0
      constraints.gridy = 0
      add(iconLabel, constraints)

      constraints.fill = GridBagPanel.Fill.Horizontal
      constraints.gridx = 1
      constraints.gridy = 0
      add(userLabel, constraints)

      constraints.fill = GridBagPanel.Fill.Horizontal
      constraints.gridx = 2
      constraints.gridy = 0
      add(nameLabel, constraints)

      constraints.fill = GridBagPanel.Fill.Horizontal
      constraints.gridwidth = 2
      constraints.gridx = 1
      constraints.gridy = 1
      add(textLabel, constraints)
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }
  }
}