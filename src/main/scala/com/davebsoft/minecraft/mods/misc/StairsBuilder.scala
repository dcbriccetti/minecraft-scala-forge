package com.davebsoft.minecraft.mods.misc

import com.davebsoft.minecraft.framework.{Chatting, CustomCommandBase}
import net.minecraft.block.Block
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks

case class StairsBuilder() extends CustomCommandBase("stairs", "s") with Chatting {
  case class MaterialPair(cube: Block, stair: Block)
  val blockPairs = Map(
    "brick"     -> MaterialPair(Blocks.brick_block,   Blocks.brick_stairs),
    "quartz"    -> MaterialPair(Blocks.quartz_block,  Blocks.quartz_stairs),
    "sandstone" -> MaterialPair(Blocks.sandstone,     Blocks.sandstone_stairs),
    "stone"     -> MaterialPair(Blocks.stone,         Blocks.stone_stairs)
  )
  def materialNames(sep: String = ", ") = blockPairs.keys.toSeq.sorted.mkString(sep)
  
  override def getCommandUsage(sender: ICommandSender) = getCommandUsage
  private def getCommandUsage = s"/$getCommandName <${materialNames(" | ")}> <length> [slope]"

  override def processCommand(sender: ICommandSender, args: Array[String]): Unit = {
    if (args.length < 2 || args.length > 3) {
      sendErrorMessage(sender, "Usage: " + getCommandUsage)
    } else {
      sender match {
        case player: EntityPlayer => 
          val world = player.worldObj
          blockPairs.get(args(0)).map(blockPair => {
            val pc = player.getPlayerCoordinates
            try {
              val slope = if (args.length == 3) args(2).toDouble else 1D
              val staircaseLength = args(1).toInt
              
              def setBlockIfAir(x: Int, y: Int, z: Int, block: Block): Unit = {
                if (world.getBlock(x, y, z) == Blocks.air) 
                  world.setBlock(x, y, z, block)
              }
              
              def placeColumn(columnIndex: Int, heightReached: Int): Unit = {
                val blockX = pc.posX + 1 + columnIndex
                val height = (columnIndex * slope).toInt
                val newLevel = heightReached < height
                0 to height - 1 foreach(yOffset => 
                  setBlockIfAir(blockX, pc.posY + yOffset, pc.posZ, blockPair.cube))
                val topBlock = if (newLevel) blockPair.stair else blockPair.cube
                setBlockIfAir(blockX, pc.posY + height, pc.posZ, topBlock)
                if (columnIndex < staircaseLength - 1)
                  placeColumn(columnIndex + 1, heightReached + (if (newLevel) 1 else 0))
              }
              
              placeColumn(0, -1)
            } catch {
              case e: NumberFormatException => sendErrorMessage(sender, "Invalid number")
            }
          }) getOrElse {
            sendErrorMessage(sender, "Material must be one of " + materialNames())
          }
        case _ =>
}}}}
