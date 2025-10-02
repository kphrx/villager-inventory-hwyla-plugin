package dev.kpherox.vihp.wthit

import mcp.mobius.waila.api.IData
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class InventoryData(val items: List<ItemStack>) : IData {
  companion object {
    private val ID = ResourceLocation.fromNamespaceAndPath("vihp", "villager_inventory")

    val TYPE = IData.createType<InventoryData>(ID)

    val CODEC =
        StreamCodec.composite(
            ItemStack.OPTIONAL_LIST_STREAM_CODEC, InventoryData::items, ::InventoryData)
  }

  override fun type() = TYPE
}
