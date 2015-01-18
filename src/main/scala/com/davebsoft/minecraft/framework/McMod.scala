package com.davebsoft.minecraft.framework

import net.minecraft.entity.Entity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityEvent

trait McMod {
  def registerOnEventBus(objects: AnyRef*): Unit = {
    objects.foreach(MinecraftForge.EVENT_BUS.register)
  }

  def ifEntity[A <: Entity](event: EntityEvent, entityClass: Class[A])(fn: => Unit): Unit = {
    println(event.entity.getClass)
    println(entityClass)
    if (! event.entity.worldObj.isRemote && event.entity.getClass == entityClass)
      fn
  }
}
