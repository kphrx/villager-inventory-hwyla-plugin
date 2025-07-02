package dev.kpherox.vihp.client.wthit

import dev.kpherox.vihp.VillagerInventoryAccessor
import mcp.mobius.waila.api.IEntityAccessor
import mcp.mobius.waila.api.IEntityComponentProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.ITooltip
import mcp.mobius.waila.api.component.ItemListComponent

object VillagerInventoryProvider : IEntityComponentProvider {
  override fun appendBody(tooltip: ITooltip, accessor: IEntityAccessor, config: IPluginConfig) {
    val inventory =
        VillagerInventoryAccessor.getWithDecode(accessor.getData().raw(), accessor.getWorld())

    tooltip.addLine(ItemListComponent(inventory))
  }
}
