package dev.kpherox.vihp

import net.minecraft.world.entity.npc.Villager

import org.slf4j.LoggerFactory
import mcp.mobius.waila.api.IClientRegistrar
import mcp.mobius.waila.api.IWailaClientPlugin

object VillagerInventoryWthitClientPlugin : IWailaClientPlugin {
	private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

	override fun register(registrar : IClientRegistrar) {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		registrar.body(VillagerInventoryProvider, Villager::class.java)
	}
}
