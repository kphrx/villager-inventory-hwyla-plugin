package dev.kpherox.vihp.client.wthit

import mcp.mobius.waila.api.IClientRegistrar
import mcp.mobius.waila.api.IWailaClientPlugin
import net.minecraft.world.entity.npc.Villager
import org.slf4j.LoggerFactory

class VillagerInventoryPlugin : IWailaClientPlugin {
  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun register(registrar: IClientRegistrar) {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    logger.info("Hello Fabric world!")
    registrar.body(VillagerInventoryProvider, Villager::class.java)
  }
}
