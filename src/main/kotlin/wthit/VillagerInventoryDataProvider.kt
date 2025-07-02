package dev.kpherox.vihp.wthit

import dev.kpherox.vihp.VillagerInventoryAccessor

import net.minecraft.world.entity.npc.Villager

import mcp.mobius.waila.api.IServerAccessor
import mcp.mobius.waila.api.IDataProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.IDataWriter

object VillagerInventoryProvider: IDataProvider<Villager> {
	override fun appendData(data: IDataWriter, accessor: IServerAccessor<Villager>, config: IPluginConfig) {
		VillagerInventoryAccessor.putWithEncode(data.raw(), accessor.getWorld(), accessor.getTarget())
	}
}
