package pip.gui

import swing.MainFrame

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 13.3.11
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */

trait DisposeOnClose extends MainFrame {
  override def closeOperation {
    dispose
  }
}