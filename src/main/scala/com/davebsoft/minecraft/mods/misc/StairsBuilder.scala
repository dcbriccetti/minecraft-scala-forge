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
  
  override def getCommandUsage(sender: ICommandSender) = s"/$getCommandName <${materialNames(" | ")}>"

  override def processCommand(sender: ICommandSender, args: Array[String]): Unit = {
    if (args.length != 1) {
      sendErrorMessage(sender, "Invalid number of arguments")
    } else {
      sender match {
        case player: EntityPlayer => 
          val world = player.worldObj
          blockPairs.get(args(0)).map(blockPair => {
            val cube = blockPair.cube
            val stairsBlock = blockPair.stair
            val pc = player.getPlayerCoordinates
            0 to 9 foreach (i => {
              val blockX = pc.posX + 1 + i
              val blockZ = pc.posZ
              0 to i - 1 foreach (j => world.setBlock(blockX, pc.posY + j, blockZ, cube))
              world.setBlock(blockX, pc.posY + i, blockZ, stairsBlock)
            })
          }) getOrElse {
            sendErrorMessage(sender, "Material must be one of " + materialNames())
          }
        case _ =>
}}}}
