package pip.core

import java.util.Properties
import java.io.{FileInputStream, InputStreamReader, File}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 27.2.11
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */

object Loc {
  import Globals._

  private val prop = new Properties

  def apply(key: String) = prop.getProperty(key)

  def load(file: String) {
    prop.load(new InputStreamReader(new FileInputStream(localizationDir+File.separator+file),encoding))
  }
}
