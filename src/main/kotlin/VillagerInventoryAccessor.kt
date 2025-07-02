package dev.kpherox.vihp

import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.Items
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.nbt.Tag
import net.minecraft.nbt.ByteArrayTag
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf

import io.netty.buffer.Unpooled

object VillagerInventoryAccessor {
	val INVENTORY_KEY = "vihp:inventory"
	val CODEC = ItemStack.OPTIONAL_LIST_STREAM_CODEC

	fun getInventory(villager: Villager) = villager.getInventory().getItems().filterNot { it.`is`(Items.AIR) }

	fun putWithEncode(data: CompoundTag, level: Level, villager: Villager) {
		val items = getInventory(villager)
		val buffer = RegistryFriendlyByteBuf(Unpooled.buffer(), level.registryAccess())
		CODEC.encode(buffer, items)
		val tag = ByteArrayTag(buffer.array())
		buffer.clear()

		data.put(INVENTORY_KEY, tag)
	}

	fun getWithDecode(data: CompoundTag, level: Level): List<ItemStack> {
		if (!data.contains(INVENTORY_KEY)) {
			return emptyList()
		}

		val buffer = RegistryFriendlyByteBuf(Unpooled.buffer(), level.registryAccess())
		buffer.writeBytes((data.get(INVENTORY_KEY) as ByteArrayTag).getAsByteArray())
		val items = CODEC.decode(buffer)
		buffer.clear()
		return items
	}
}
