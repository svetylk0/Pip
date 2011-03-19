package pip.gui

import java.awt.Color
import actors.Actor.actor
import swing.{UIElement, Component}
import math.round

/**
 * Created by IntelliJ IDEA.
 * User: svetylk0@seznam.cz
 * Date: 19.3.11
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */

object Animations {


  def backgroundColorTransition(fromColor: Color,
                                toColor: Color,
                                targetComponent: UIElement,
                                repaintComponent: UIElement) {
    actor {
      def getRange(min: Double, max: Double, pieces: Double = 50d) = {
        if (min == max) List.fill(round(pieces).toInt)(round(max).toInt)
        else ((min to max by ((max - min) / pieces) toList) drop 1) map {
          x => round(x).toInt
        }
      }

      val rList = getRange(fromColor.getRed, toColor.getRed)
      val gList = getRange(fromColor.getGreen, toColor.getGreen)
      val bList = getRange(fromColor.getBlue, toColor.getBlue)

      for (((r, g), b) <- rList zip gList zip bList) {
        targetComponent.background = new Color(r, g, b)
        repaintComponent.repaint
        Thread.sleep(10)
      }
    }
  }
}