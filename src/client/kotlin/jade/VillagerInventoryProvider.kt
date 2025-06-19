package dev.kpherox.vihp.jade

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.ResourceLocation
import net.minecraft.network.chat.Component

import snownee.jade.api.EntityAccessor
import snownee.jade.api.config.IPluginConfig
import snownee.jade.api.ITooltip
import snownee.jade.api.ui.JadeUI
import snownee.jade.api.ui.Element
import snownee.jade.api.ui.IDisplayHelper
import snownee.jade.api.IEntityComponentProvider

object VillagerInventoryProvider: IEntityComponentProvider {
	override fun getUid(): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath("vihp", "villager_inventory")
	}

	override fun appendTooltip(tooltip: ITooltip, accessor: EntityAccessor, config: IPluginConfig) {
		val inventory = (accessor.getEntity() as Villager).getInventory().getItems()

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
}
