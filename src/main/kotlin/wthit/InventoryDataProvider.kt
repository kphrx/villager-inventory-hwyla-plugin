package dev.kpherox.vihp.wthit

import dev.kpherox.vihp.InventoryAccessor
import mcp.mobius.waila.api.IDataProvider
import mcp.mobius.waila.api.IDataWriter
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.IServerAccessor
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.npc.InventoryCarrier

object InventoryDataProvider : IDataProvider<PathfinderMob> {
  override fun appendData(
      data: IDataWriter,
      accessor: IServerAccessor<PathfinderMob>,
      config: IPluginConfig
  ) {
    val entity = accessor.getTarget()
    if (entity is InventoryCarrier) {
      val inventory = InventoryAccessor.getInventory(entity)
      data.addImmediate(InventoryData(inventory))
    }
  }
}
