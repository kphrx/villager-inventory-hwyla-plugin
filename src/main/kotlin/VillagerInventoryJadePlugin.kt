package dev.kpherox.vihp

import org.slf4j.LoggerFactory
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
object VillagerInventoryJadePlugin : IWailaPlugin {
	private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

	override fun register(registration : IWailaCommonRegistration) {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}

	override fun registerClient(registration : IWailaClientRegistration) {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}
}
