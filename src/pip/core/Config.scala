package pip.core

import java.util.Properties
import java.io.{FileOutputStream, OutputStreamWriter, FileInputStream, InputStreamReader}

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 12.3.11
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */

object Config {
  import Globals._

  val prop = new Properties

  def loadConfig {
    prop.clear
    prop.load(new InputStreamReader(new FileInputStream(configFile),encoding))
  }

  def saveConfig {
    prop.store(new OutputStreamWriter(new FileOutputStream(configFile),encoding),"Pip config")
  }

  def apply(property: String) = prop.getProperty(property)

  def update(property: String, value: String) {
    prop.setProperty(property, value)
  }

}
