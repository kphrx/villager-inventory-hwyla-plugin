package dev.kpherox.vihp.wthit

import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.Items
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.nbt.Tag
import net.minecraft.nbt.ByteArrayTag
import net.minecraft.network.RegistryFriendlyByteBuf

import io.netty.buffer.Unpooled

import mcp.mobius.waila.api.IServerAccessor
import mcp.mobius.waila.api.IDataProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.IDataWriter
import mcp.mobius.waila.api.data.ItemData

object VillagerInventoryProvider: IDataProvider<Villager> {
	val INVENTORY_KEY = "vihp:inventory"

	override fun appendData(data: IDataWriter, accessor: IServerAccessor<Villager>, config: IPluginConfig) {
		val inventory = getInventory(accessor.getTarget())

		data.raw().put(VillagerInventoryProvider.INVENTORY_KEY, encode(accessor.getWorld(), inventory))
	}

	private fun getInventory(villager: Villager) = villager.getInventory().getItems().filterNot { it.`is`(Items.AIR) }

	fun encode(level: Level, items: List<ItemStack>): Tag {
		val buffer = RegistryFriendlyByteBuf(Unpooled.buffer(), level.registryAccess())
		ItemStack.OPTIONAL_LIST_STREAM_CODEC.encode(buffer, items)
		val tag = ByteArrayTag(buffer.array())
		buffer.clear()
		return tag
	}
}
