package com.davebsoft.minecraft.mods.misc

import net.minecraft.world.World

import scala.util.Random
import com.davebsoft.minecraft.framework.CustomCommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.passive._
import net.minecraft.entity.player.EntityPlayer

case class MultiSpawn() extends CustomCommandBase("multispawn", "ms") {
  private val random = new Random
  private val entityClasses = 
    Seq(classOf[EntityChicken], classOf[EntityCow], classOf[EntityHorse], classOf[EntityWolf], 
      classOf[EntityPig], classOf[EntitySheep])
  
  override def getCommandUsage(sender: ICommandSender) = s"/${names.get(0)} <number of entities>"

  override def processCommand(sender: ICommandSender, args: Array[String]): Unit = {
    if (args.length != 1) {
      sendErrorMessage(sender, "Invalid number of arguments")
    } else {
      sender match {
        case player: EntityPlayer =>
          try {
            val numberOfEntities = Integer.parseInt(args(0))
            1 to numberOfEntities foreach(i => {
              val randomIndex = random.nextInt(entityClasses.size)
              val randomClass = entityClasses(randomIndex)
              val entity = randomClass.getConstructor(classOf[World]).newInstance(player.worldObj)
              entity.setLocationAndAngles(player.posX, player.posY, player.posZ, 0, 0)
              player.worldObj.spawnEntityInWorld(entity)
            })
          } catch {
            case e: NumberFormatException => sendErrorMessage(sender, s"${args(0)} is not a valid number")
          }
        case _ => // Not sent by player
      }
    }
  }
}
