package dev.kpherox.vihp.jade

import dev.kpherox.vihp.VillagerInventoryAccessor
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import snownee.jade.api.EntityAccessor
import snownee.jade.api.IServerDataProvider

object VillagerInventoryProvider : IServerDataProvider<EntityAccessor> {
  override fun getUid() = VillagerInventoryPlugin.INVENTORY

  override fun appendServerData(data: CompoundTag, accessor: EntityAccessor) {
    val inventory = VillagerInventoryAccessor.getInventory(accessor.getEntity() as Villager)

    data.put(
        VillagerInventoryPlugin.INVENTORY_KEY,
        accessor.encodeAsNbt(ItemStack.OPTIONAL_LIST_STREAM_CODEC, inventory))
  }
}
