package dev.kpherox.vihp.client.jade

import dev.kpherox.vihp.VillagerInventoryAccessor
import dev.kpherox.vihp.jade.VillagerInventoryPlugin as VillagerInventoryServerPlugin
import kotlin.jvm.optionals.getOrDefault
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
    if (!data.contains(VillagerInventoryAccessor.INVENTORY_KEY)) {
      return
    }

    val inventory =
        accessor.decodeFromNbt(
            VillagerInventoryAccessor.CODEC, data.get(VillagerInventoryAccessor.INVENTORY_KEY))

    val elements = arrayListOf<Element>()
    for (itemStack in inventory.getOrDefault(emptyList())) {
      elements.add(JadeUI.item(itemStack))
    }
    tooltip.add(elements)
  }
}
