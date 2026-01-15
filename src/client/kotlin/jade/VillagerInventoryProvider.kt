package dev.kpherox.vihp.client.jade

import dev.kpherox.vihp.jade.VillagerInventoryPlugin as VillagerInventoryServerPlugin
import net.minecraft.world.item.ItemStack
import snownee.jade.api.EntityAccessor
import snownee.jade.api.IEntityComponentProvider
import snownee.jade.api.ITooltip
import snownee.jade.api.config.IPluginConfig
import snownee.jade.api.ui.JadeUI

object VillagerInventoryProvider : IEntityComponentProvider {
  override fun getUid() = VillagerInventoryServerPlugin.INVENTORY

  override fun appendTooltip(tooltip: ITooltip, accessor: EntityAccessor, config: IPluginConfig) {
    accessor.getServerData().get(VillagerInventoryServerPlugin.INVENTORY_KEY)?.let { tag ->
      accessor.decodeFromNbt(ItemStack.OPTIONAL_LIST_STREAM_CODEC, tag).ifPresent { inventory ->
        tooltip.add(inventory.map(JadeUI::item))
      }
    }
  }
}
