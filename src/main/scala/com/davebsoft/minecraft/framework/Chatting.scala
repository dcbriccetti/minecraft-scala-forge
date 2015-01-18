package com.davebsoft.minecraft.framework

import net.minecraft.command.ICommandSender
import net.minecraft.util.{EnumChatFormatting, ChatComponentText}

trait Chatting {

  def sendErrorMessage(sender: ICommandSender, message: String) =
    sendMessage(sender, EnumChatFormatting.DARK_RED, message)

  def sendOkMessage(sender: ICommandSender, message: String) =
    sendMessage(sender, EnumChatFormatting.GREEN, message)

  def sendMessage(sender: ICommandSender, formatting: EnumChatFormatting, message: String): Unit = {
    sender.addChatMessage(new ChatComponentText(formatting + message))
  }
}
