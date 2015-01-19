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
        -2 to 2 foreach(xOff => {
          -2 to 2 foreach (yOff => {
            world.spawnEntityInWorld(makeArrow(world, snowball, xOff, yOff))
          })
        })
        snowball.setDead()
      case _ =>
    }
	}

  private def makeArrow(world: World, snowball: EntitySnowball, xOff: Int, yOff: Int) =
    new EntityArrow(world) {
      setLocationAndAngles(snowball.posX + xOff, snowball.posY + yOff, snowball.posZ, 0, 0)
      motionX = snowball.motionX
      motionY = snowball.motionY
      motionZ = snowball.motionZ
    }
}
