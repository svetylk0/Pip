package pip.gui
import scala.swing.Component
import javax.swing.JComboBox

class MutableComboBox(i: Seq[String]) extends Component {
  override lazy val peer = new JComboBox
  
  def add(s: String) = peer.addItem(s)
  
  def remove(s: String) = peer.removeItem(s)

  def item = peer.getSelectedItem.asInstanceOf[String]
  
  def items = peer.getItemCount
  
  //oklikou pridat prvky
  i foreach add
}