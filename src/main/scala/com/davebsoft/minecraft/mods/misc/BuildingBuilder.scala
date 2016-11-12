package com.davebsoft.minecraft.mods.misc

import com.davebsoft.minecraft.framework.CustomCommandBase
import net.minecraft.block.Block
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer

case class BuildingBuilder() extends CustomCommandBase("buildbuilding", "bb") {
  override def getCommandUsage(sender: ICommandSender) = s"/$getCommandName <block name or ID>"

  override def processCommand(sender: ICommandSender, args: Array[String]): Unit = {
    if (args.length != 1) {
      sendErrorMessage(sender, "Invalid number of arguments")
    } else {
      sender match {
        case player: EntityPlayer =>
          val pc = player.getPlayerCoordinates
          try {
            val block = Option(Block.getBlockFromName(args(0))).getOrElse(
              Block.getBlockById(Integer.parseInt(args(0))))
            
            def makeWallOnX(z: Int, height: Int, length: Int) {
              0 until height foreach(y => 
                0 until length foreach(x => player.worldObj.setBlock(pc.posX + x, pc.posY + y, pc.posZ + z, block))
              )
            }
            def makeWallOnZ(x: Int, height: Int, length: Int) {
              0 until height foreach(y => 
                0 until length foreach(z => player.worldObj.setBlock(pc.posX + x, pc.posY + y, pc.posZ + z, block))
              )
            }
           
            makeWallOnX(0, 2, 5)
            makeWallOnZ(0, 2, 5)
            makeWallOnX(5, 2, 5)
            makeWallOnZ(5, 2, 5)
          
          } catch {
            case e: NumberFormatException =>
              sendErrorMessage(sender, s"${args(0)} is not a valid block name or ID")
          }
        case _ =>
      }
  }}
}
