package dev.kpherox.vihp

import net.minecraft.world.entity.npc.InventoryCarrier
import net.minecraft.world.item.Items

object InventoryAccessor {
  fun getInventory(entity: InventoryCarrier) =
      entity.getInventory().getItems().filterNot { it.`is`(Items.AIR) }
}
