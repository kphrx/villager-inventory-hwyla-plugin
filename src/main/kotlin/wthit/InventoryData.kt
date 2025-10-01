package dev.kpherox.vihp.wthit

import mcp.mobius.waila.api.IData
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.data.ItemData
import mcp.mobius.waila.plugin.extra.data.ItemDataImpl
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class InventoryData(private val rawValue: ItemDataImpl) : IData {
  companion object {
    private val ID = ResourceLocation.fromNamespaceAndPath("vihp", "villager_inventory")

    val TYPE = IData.createType<InventoryData>(ID)

    val CODEC: StreamCodec<RegistryFriendlyByteBuf, InventoryData> =
        StreamCodec.composite(ItemDataImpl.CODEC, InventoryData::rawValue, ::InventoryData)

    fun of(config: IPluginConfig) = InventoryData(ItemData.of(config) as ItemDataImpl)
  }

  fun add(stacks: List<ItemStack>) = rawValue.add(stacks)

  fun items() = rawValue.items()

  override fun type() = TYPE
}
