package dev.kpherox.vihp.wthit

import net.minecraft.world.entity.npc.Villager

import mcp.mobius.waila.api.IEntityAccessor
import mcp.mobius.waila.api.IEntityComponentProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.ITooltip

object VillagerInventoryProvider: IEntityComponentProvider {
	override fun appendBody(tooltip: ITooltip, accessor: IEntityAccessor, config: IPluginConfig) {
		val inventory = (accessor.getEntity() as Villager).getInventory().getItems()

		for (itemStack in inventory) {
			tooltip.addLine(itemStack.getHoverName());
		}
	}
}
