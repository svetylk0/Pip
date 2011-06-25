package pip.core
import java.io.File
import javax.swing.ImageIcon
import java.net.URL
import java.io.FileOutputStream

object ImagesCache {
  private val dir = new File(Globals.imagesCacheDir)
  private val dirPath = dir.getAbsolutePath+File.separator
  
  def contains(file: String) = dir.list contains file
  
  def get(file: String) = dir.listFiles find ( _.getName == file) match {
    case Some(f) => new ImageIcon(f.getAbsolutePath)
    case None => new ImageIcon(dirPath+"empty") 
  }

  def save(url: URL, file: String) {
    val out = new FileOutputStream(dirPath+file)
    Tools.transferStream(url.openStream,out)
  }
}