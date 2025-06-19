package dev.kpherox.vihp

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.ResourceLocation
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.layouts.LayoutElement
import net.minecraft.client.gui.narration.NarrationSupplier
import net.minecraft.network.chat.Component

import snownee.jade.api.ITooltip
import snownee.jade.api.EntityAccessor
import snownee.jade.api.config.IPluginConfig as IJadePluginConfig
import snownee.jade.api.ITooltip as IJadeTooltip
import snownee.jade.api.ui.JadeUI
import snownee.jade.api.ui.Element
import snownee.jade.api.ui.IDisplayHelper
import snownee.jade.api.IEntityComponentProvider as IJadeEntityComponentProvider


import mcp.mobius.waila.api.IEntityAccessor
import mcp.mobius.waila.api.IEntityComponentProvider as IWthitEntityComponentProvider
import mcp.mobius.waila.api.IPluginConfig as IWthitPluginConfig
import mcp.mobius.waila.api.ITooltip as IWthitTooltip

interface IEntityComponentProvider: IJadeEntityComponentProvider, IWthitEntityComponentProvider {}

object VillagerInventoryProvider: IEntityComponentProvider {
	override fun getUid(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath("vihp", "villager_inventory")
	}

	override fun appendTooltip(tooltip: IJadeTooltip, accessor: EntityAccessor, config: IJadePluginConfig) {
		val inventory = getInventory(accessor.getEntity() as Villager)
		val elements = arrayListOf<Element>()

		for (itemStack in inventory) {
			// UNIVERSAL_ITEM_STORAGE_SHOW_NAME_AMOUNT
			// UNIVERSAL_ITEM_STORAGE_ITEMS_PER_LINE
			elements.add(JadeUI.smallItem(itemStack).refreshNarration())
			val s = IDisplayHelper.get().humanReadableNumber(itemStack.getCount().toDouble(), "", false, null)
			val width = Minecraft.getInstance().font.width(s)
			elements.add(JadeUI.text(Component.literal(s).append("× ").append(IDisplayHelper.get().stripColor(itemStack.getHoverName()))).narration(""))

			// elements.add(JadeUI.item(stack))
		}

		tooltip.add(elements)
	}

	override fun appendBody(tooltip: IWthitTooltip, accessor: IEntityAccessor, config: IWthitPluginConfig) {
		val inventory = getInventory(accessor.getEntity())
		for (itemStack in inventory) {
			tooltip.addLine(itemStack.getHoverName());
		}
	}

	private fun getInventory(villager: Villager): List<ItemStack> {
		return villager.getInventory().getItems()
	}
}
