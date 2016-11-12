package com.davebsoft.minecraft.mods.misc

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.projectile.{EntityArrow, EntitySnowball}
import net.minecraft.world.World
import net.minecraftforge.event.entity.EntityJoinWorldEvent

case class SharpSnowballArray() {
	@SubscribeEvent
	def replaceSnowballWithArrow(event: EntityJoinWorldEvent) {
    val entity = event.entity
    val world = entity.worldObj
		entity match {
      case snowball: EntitySnowball if ! world.isRemote =>
        -2 to 2 foreach { xOff =>
          -2 to 2 foreach { yOff =>
            world.spawnEntityInWorld(makeArrow(world, snowball, xOff, yOff))
          }
        }
        snowball.setDead()
      case _ =>
    }
	}

  private def makeArrow(world: World, snowball: EntitySnowball, xOff: Int, yOff: Int) = {
    val (mx, my, mz) = (snowball.motionX, snowball.motionY, snowball.motionZ)

    val v = math.sqrt(mx * mx + my * my + mz * mz)
    val r = math.sqrt(mx * mx + mz * mz)
    val yaw = math.acos(mx / r) + (if (mz < 0) 180 else 0)
    val pitch = {
      val p = math.asin(my / v)
      if (p > 90) p - 180 else p
    }
    val (x, y, z) = rotate(xOff, yOff, 0, yaw, pitch)

    new EntityArrow(world) {
      setLocationAndAngles(snowball.posX + x, snowball.posY + y, snowball.posZ + z, 0, 0)
      motionX = mx
      motionY = my
      motionZ = mz
    }
  }
  
  private def rotate(x: Double, y: Double, z: Double, yaw: Double, pitch: Double) = {
    val sinYaw = math.sin(yaw)
    val cosYaw = math.cos(yaw)
    val sinPitch = math.sin(pitch)
    val cosPitch = math.cos(pitch)
    (
      x * cosYaw - z * sinYaw,
      y * cosPitch + z * cosYaw * sinPitch + x * sinYaw * sinPitch,
      z * cosYaw * cosPitch + x * cosPitch * sinYaw - y * sinPitch
    )
  }
}
