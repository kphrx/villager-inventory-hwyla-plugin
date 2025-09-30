package dev.kpherox.vihp.client.wthit

import mcp.mobius.waila.api.IClientRegistrar
import mcp.mobius.waila.api.IWailaClientPlugin
import net.minecraft.world.entity.npc.Villager
import org.slf4j.LoggerFactory

class VillagerInventoryPlugin : IWailaClientPlugin {
  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun register(registrar: IClientRegistrar) {
    logger.info("[Villager Inventory Plugin for WTHIT] Client side startup")
    registrar.body(VillagerInventoryProvider, Villager::class.java)
  }
}
