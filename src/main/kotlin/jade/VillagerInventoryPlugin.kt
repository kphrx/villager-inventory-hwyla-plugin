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

  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun register(registration: IWailaCommonRegistration) {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    logger.info("Hello Fabric world!")
    registration.registerEntityDataProvider(VillagerInventoryProvider, Villager::class.java)
  }
}
