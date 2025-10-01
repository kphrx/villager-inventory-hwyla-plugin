package dev.kpherox.vihp.client.wthit

import dev.kpherox.vihp.wthit.VillagerInventoryPlugin
import mcp.mobius.waila.api.IEntityAccessor
import mcp.mobius.waila.api.IEntityComponentProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.ITooltip
import mcp.mobius.waila.api.component.ItemListComponent

object VillagerInventoryProvider : IEntityComponentProvider {
  override fun appendBody(tooltip: ITooltip, accessor: IEntityAccessor, config: IPluginConfig) {
    val itemData = accessor.getData().get(VillagerInventoryPlugin.INVENTORY_TYPE)
    if (itemData != null) {
      val inventory = itemData.items()
      tooltip.addLine(ItemListComponent(inventory))
    }
  }
}
