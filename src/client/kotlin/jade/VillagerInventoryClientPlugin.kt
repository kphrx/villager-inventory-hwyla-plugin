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
    logger.info("[Villager Inventory Plugin for Jade] Client side startup")
    registration.registerEntityComponent(VillagerInventoryProvider, Villager::class.java)
  }
}
