package com.davebsoft.minecraft.mods.misc

trait StairTransform {
  def stairMetadata(yaw: Int) = {
    def wrapped(heading: Int) = (heading + 360) % 360
    val offsetHeading = wrapped(yaw + 45)
    val md =
      if      (offsetHeading > 270) 0
      else if (offsetHeading > 180) 3
      else if (offsetHeading > 90)  1
      else                          2
    println(s"Yaw: $yaw, sy: $offsetHeading, metadata: $md")
    md
  }
}
