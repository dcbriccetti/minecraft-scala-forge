package com.davebsoft.minecraft.mods.misc

import com.davebsoft.minecraft.framework.McMod
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.creativetab.CreativeTabs

@Mod(modid = "davemod", name = "Dave's Mod", version = "0.1", modLanguage = "scala")
object DaveMod extends McMod {

  val genericItem = new GenericItem().
    setMaxStackSize(64).
    setCreativeTab(CreativeTabs.tabMisc).
    setUnlocalizedName("genericItem").
    setTextureName("davemod:genericItem")

  val daveBlock = new DaveBlock()

  @EventHandler
  def init(event: FMLInitializationEvent) {
    registerOnEventBus()
  }

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) = {
    GameRegistry.registerItem(genericItem, "genericItem")
    GameRegistry.registerBlock(daveBlock, "daveBlock")
  }

  @EventHandler
  def serverInit(event: FMLServerStartingEvent): Unit = {
    event.registerServerCommand(MultiSpawn())
  }
}
