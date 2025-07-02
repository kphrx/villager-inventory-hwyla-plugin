package dev.kpherox.vihp.jade

import dev.kpherox.vihp.VillagerInventoryAccessor

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.npc.Villager

import snownee.jade.api.IServerDataProvider
import snownee.jade.api.EntityAccessor

object VillagerInventoryProvider: IServerDataProvider<EntityAccessor> {
	override fun getUid() = VillagerInventoryPlugin.INVENTORY

	override fun appendServerData(data: CompoundTag, accessor: EntityAccessor) {
		val inventory = VillagerInventoryAccessor.getInventory(accessor.getEntity() as Villager)

		data.put(VillagerInventoryAccessor.INVENTORY_KEY, accessor.encodeAsNbt(VillagerInventoryAccessor.CODEC, inventory));
	}
}
