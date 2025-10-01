package dev.kpherox.vihp.client.jade

import dev.kpherox.vihp.jade.VillagerInventoryPlugin as VillagerInventoryServerPlugin
import kotlin.jvm.optionals.getOrDefault
import net.minecraft.world.item.ItemStack
import snownee.jade.api.EntityAccessor
import snownee.jade.api.IEntityComponentProvider
import snownee.jade.api.ITooltip
import snownee.jade.api.config.IPluginConfig
import snownee.jade.api.ui.Element
import snownee.jade.api.ui.JadeUI

object VillagerInventoryProvider : IEntityComponentProvider {
  override fun getUid() = VillagerInventoryServerPlugin.INVENTORY

  override fun appendTooltip(tooltip: ITooltip, accessor: EntityAccessor, config: IPluginConfig) {
    val data = accessor.getServerData()
    if (!data.contains(VillagerInventoryServerPlugin.INVENTORY_KEY)) {
      return
    }

    val inventory =
        accessor.decodeFromNbt(
            ItemStack.OPTIONAL_LIST_STREAM_CODEC,
            data.get(VillagerInventoryServerPlugin.INVENTORY_KEY))

    val elements = arrayListOf<Element>()
    for (itemStack in inventory.getOrDefault(emptyList())) {
      elements.add(JadeUI.item(itemStack))
    }
    tooltip.add(elements)
  }
}
