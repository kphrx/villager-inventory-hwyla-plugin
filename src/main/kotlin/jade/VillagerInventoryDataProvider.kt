package dev.kpherox.vihp.jade

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.resources.ResourceLocation

import snownee.jade.api.IServerDataProvider
import snownee.jade.api.EntityAccessor

object VillagerInventoryProvider: IServerDataProvider<EntityAccessor> {
	val INVENTORY_KEY = "vihp:inventory"

	override fun getUid(): ResourceLocation {
		return VillagerInventoryPlugin.INVENTORY
	}

	override fun appendServerData(data: CompoundTag, accessor: EntityAccessor) {
		val inventory = getInventory(accessor.getEntity() as Villager)

		data.put(VillagerInventoryProvider.INVENTORY_KEY, accessor.encodeAsNbt(ItemStack.OPTIONAL_LIST_STREAM_CODEC, inventory));
	}

	private fun getInventory(villager: Villager): List<ItemStack> {
		return villager.getInventory().getItems().filterNot { it.`is`(Items.AIR) }
	}
}
