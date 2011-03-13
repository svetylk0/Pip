package pip.gui

import scala.swing._

object Tabs extends SimpleSwingApplication {
  val ta1 = new TextArea
  val b  = new Button("OK") 
  val tp1 = new TabbedPane.Page("First tab", ta1)
  val tp2 = new TabbedPane.Page("Second tab", b)
  def top = new MainFrame {
    title = "Tabs demo"
    contents = new TabbedPane {
      pages += tp1
      pages += tp2
    }
  }
}
