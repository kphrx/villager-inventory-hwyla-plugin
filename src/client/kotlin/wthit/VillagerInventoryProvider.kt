package dev.kpherox.vihp.client.wthit

import mcp.mobius.waila.api.IEntityAccessor
import mcp.mobius.waila.api.IEntityComponentProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.ITooltip
import mcp.mobius.waila.api.component.ItemListComponent
import mcp.mobius.waila.api.data.ItemData
import mcp.mobius.waila.plugin.extra.data.ItemDataImpl

object VillagerInventoryProvider : IEntityComponentProvider {
  override fun appendBody(tooltip: ITooltip, accessor: IEntityAccessor, config: IPluginConfig) {
    val itemData = accessor.getData().get(ItemData.TYPE) as ItemDataImpl
    val inventory = itemData.items()

    tooltip.addLine(ItemListComponent(inventory))
  }
}
