package dev.kpherox.vihp.client.jade

import net.minecraft.world.entity.npc.Villager
import org.slf4j.LoggerFactory
import snownee.jade.api.IWailaClientRegistration
import snownee.jade.api.IWailaPlugin
import snownee.jade.api.WailaPlugin

@WailaPlugin
object VillagerInventoryPlugin : IWailaPlugin {
  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun registerClient(registration: IWailaClientRegistration) {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    logger.info("Hello Fabric world!")
    registration.registerEntityComponent(VillagerInventoryProvider, Villager::class.java)
  }
}
