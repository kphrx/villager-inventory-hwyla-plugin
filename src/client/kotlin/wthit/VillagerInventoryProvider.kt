package dev.kpherox.vihp.client.wthit

import dev.kpherox.vihp.wthit.VillagerInventoryProvider as VillagerInventoryDataProvider

import io.netty.buffer.Unpooled

import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.level.Level
import net.minecraft.nbt.ByteArrayTag
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack
import net.minecraft.network.RegistryFriendlyByteBuf

import mcp.mobius.waila.api.IEntityAccessor
import mcp.mobius.waila.api.IEntityComponentProvider
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.ITooltip

object VillagerInventoryProvider: IEntityComponentProvider {
	override fun appendBody(tooltip: ITooltip, accessor: IEntityAccessor, config: IPluginConfig) {
		val tag = accessor.getData().raw().get(VillagerInventoryDataProvider.INVENTORY_KEY)
		if (tag == null) {
			return
		}
		val inventory = decode(accessor.getWorld(), tag)

		for (itemStack in inventory) {
			tooltip.addLine(itemStack.getHoverName());
		}
	}

	fun decode(level: Level, tag: Tag): List<ItemStack> {
		val buffer = RegistryFriendlyByteBuf(Unpooled.buffer(), level.registryAccess())
		buffer.writeBytes((tag as ByteArrayTag).getAsByteArray())
		val items = ItemStack.OPTIONAL_LIST_STREAM_CODEC.decode(buffer)
		buffer.clear()
		return items
	}
}
