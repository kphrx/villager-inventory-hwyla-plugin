package dev.kpherox.vihp

import org.slf4j.LoggerFactory
import mcp.mobius.waila.api.ICommonRegistrar;
import mcp.mobius.waila.api.IWailaCommonPlugin;

object VillagerInventoryWthitCommonPlugin : IWailaCommonPlugin {
	private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

	override fun register(registrar : ICommonRegistrar) {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}
}
