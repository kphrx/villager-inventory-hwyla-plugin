package dev.kpherox.vihp.client.jade

import kotlin.jvm.optionals.getOrDefault

import dev.kpherox.vihp.jade.VillagerInventoryPlugin as VillagerInventoryServerPlugin
import dev.kpherox.vihp.jade.VillagerInventoryProvider as VillagerInventoryDataProvider

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import net.minecraft.network.chat.Component

import snownee.jade.api.EntityAccessor
import snownee.jade.api.config.IPluginConfig
import snownee.jade.api.ITooltip
import snownee.jade.api.ui.JadeUI
import snownee.jade.api.ui.Element
import snownee.jade.api.ui.IDisplayHelper
import snownee.jade.api.IEntityComponentProvider

object VillagerInventoryProvider: IEntityComponentProvider {
	override fun getUid() = VillagerInventoryServerPlugin.INVENTORY

	override fun appendTooltip(tooltip: ITooltip, accessor: EntityAccessor, config: IPluginConfig) {
		val data = accessor.getServerData()
		if (!data.contains(VillagerInventoryDataProvider.INVENTORY_KEY)) {
			return
		}

		val inventory = accessor.decodeFromNbt<List<ItemStack>>(ItemStack.OPTIONAL_LIST_STREAM_CODEC, data.get(VillagerInventoryDataProvider.INVENTORY_KEY))

		val elements = arrayListOf<Element>()
		for (itemStack in inventory.getOrDefault(emptyList())) {
			elements.add(JadeUI.item(itemStack))
		}
		tooltip.add(elements)
	}
}
