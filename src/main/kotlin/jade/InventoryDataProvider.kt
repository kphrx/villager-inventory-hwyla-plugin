package dev.kpherox.vihp.jade

import dev.kpherox.vihp.InventoryAccessor
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.npc.InventoryCarrier
import net.minecraft.world.item.ItemStack
import snownee.jade.api.EntityAccessor
import snownee.jade.api.IServerDataProvider

object InventoryDataProvider : IServerDataProvider<EntityAccessor> {
  override fun getUid() = VillagerInventoryPlugin.INVENTORY

  override fun appendServerData(data: CompoundTag, accessor: EntityAccessor) {
    val entity = accessor.getEntity()
    if (entity is InventoryCarrier) {
      val inventory = InventoryAccessor.getInventory(entity)
      data.put(
          VillagerInventoryPlugin.INVENTORY_KEY,
          accessor.encodeAsNbt(ItemStack.OPTIONAL_LIST_STREAM_CODEC, inventory))
    }
  }
}
