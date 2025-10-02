package dev.kpherox.vihp.wthit

import dev.kpherox.vihp.VillagerInventoryAccessor
import mcp.mobius.waila.api.IDataProvider
import mcp.mobius.waila.api.IDataWriter
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.IServerAccessor
import net.minecraft.world.entity.npc.Villager

object VillagerInventoryProvider : IDataProvider<Villager> {
  override fun appendData(
      data: IDataWriter,
      accessor: IServerAccessor<Villager>,
      config: IPluginConfig
  ) {
    val inventory = VillagerInventoryAccessor.getInventory(accessor.getTarget())

    data.addImmediate(InventoryData(inventory))
  }
}
