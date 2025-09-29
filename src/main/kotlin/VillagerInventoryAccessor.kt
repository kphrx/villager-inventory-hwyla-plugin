package dev.kpherox.vihp

import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.Items

object VillagerInventoryAccessor {
  fun getInventory(villager: Villager) =
      villager.getInventory().getItems().filterNot { it.`is`(Items.AIR) }
}
