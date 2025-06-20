package dev.kpherox.vihp.jade

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.ResourceLocation

import snownee.jade.api.IServerDataProvider
import snownee.jade.api.EntityAccessor

object VillagerInventoryProvider: IServerDataProvider<EntityAccessor> {
	val INVENTORY_KEY = "vihp:inventory"

	override fun getUid(): ResourceLocation {
		return VillagerInventoryPlugin.INVENTORY
	}

	override fun appendServerData(data: CompoundTag, accessor: EntityAccessor) {
		val villager = (accessor.getEntity() as Villager)
		val inventory = villager.getInventory().getItems()

		data.put(VillagerInventoryProvider.INVENTORY_KEY, accessor.encodeAsNbt(ItemStack.OPTIONAL_LIST_STREAM_CODEC, inventory));
	}
}
