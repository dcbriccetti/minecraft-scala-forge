package com.davebsoft.minecraft.framework

import net.minecraft.command.{CommandBase, ICommandSender}
import net.minecraft.entity.player.EntityPlayer

import scala.collection.JavaConversions._

abstract class CustomCommandBase(cmdNames: String*) extends CommandBase with Chatting {
  val names: java.util.List[String] = cmdNames

  override def getCommandName = names.get(0)

  override def getCommandAliases = names

  override def canCommandSenderUseCommand(sender: ICommandSender) = sender.isInstanceOf[EntityPlayer]
}
