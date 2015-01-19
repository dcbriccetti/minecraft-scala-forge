package com.davebsoft.minecraft.mods.misc

import java.util.Random

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

class DaveBlock() extends Block(Material.rock) {
  setHarvestLevel("pickaxe", 0) 
  setHardness(4F)
  setStepSound(Block.soundTypeGravel)
  setBlockName("daveBlock")
  setBlockTextureName("davemod:daveBlock")
  setCreativeTab(CreativeTabs.tabBlock)
  setLightLevel(5F)
}
