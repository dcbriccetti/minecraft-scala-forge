package com.davebsoft.minecraft.mods.misc

import com.davebsoft.minecraft.framework.McMod
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import cpw.mods.fml.common.registry.GameRegistry

@Mod(modid = "davemod", name = "Dave's Mod", version = "0.1", modLanguage = "scala")
object DaveMod extends McMod {

  val daveBlock = new DaveBlock()

  @EventHandler
  def init(event: FMLInitializationEvent) {
    registerOnEventBus(SharpSnowballArray)
  }

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) = {
    GameRegistry.registerBlock(daveBlock, "daveBlock")
  }

  @EventHandler
  def serverInit(event: FMLServerStartingEvent): Unit = {
    event.registerServerCommand(MultiSpawn())
  }
}
