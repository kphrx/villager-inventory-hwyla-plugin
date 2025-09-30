package dev.kpherox.vihp.jade

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.npc.Villager
import org.slf4j.LoggerFactory
import snownee.jade.api.IWailaCommonRegistration
import snownee.jade.api.IWailaPlugin
import snownee.jade.api.WailaPlugin

@WailaPlugin
object VillagerInventoryPlugin : IWailaPlugin {
  val INVENTORY = ResourceLocation.fromNamespaceAndPath("vihp", "villager_inventory")

  val INVENTORY_KEY = "vihp:inventory"

  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun register(registration: IWailaCommonRegistration) {
    logger.info("[Villager Inventory Plugin for Jade] Server side startup")
    registration.registerEntityDataProvider(VillagerInventoryProvider, Villager::class.java)
  }
}
