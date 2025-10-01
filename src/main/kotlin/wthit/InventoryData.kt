package dev.kpherox.vihp.wthit

import mcp.mobius.waila.api.IData
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.data.ItemData
import mcp.mobius.waila.plugin.extra.data.ItemDataImpl
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

class InventoryData(val rawValue: ItemDataImpl) : IData {
  companion object {
    val CODEC =
        StreamCodec.composite<RegistryFriendlyByteBuf, InventoryData, ItemDataImpl>(
            ItemDataImpl.CODEC, InventoryData::rawValue, ::InventoryData)

    fun of(config: IPluginConfig) = InventoryData(ItemData.of(config) as ItemDataImpl)
  }

  fun add(stacks: List<ItemStack>) = rawValue.add(stacks)

  fun items() = rawValue.items()

  override fun type() = VillagerInventoryPlugin.INVENTORY_TYPE
}
