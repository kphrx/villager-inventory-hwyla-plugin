package dev.kpherox.vihp.wthit

import mcp.mobius.waila.api.ICommonRegistrar
import mcp.mobius.waila.api.IWailaCommonPlugin
import net.minecraft.world.entity.npc.Villager
import org.slf4j.LoggerFactory

class VillagerInventoryPlugin : IWailaCommonPlugin {
  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun register(registrar: ICommonRegistrar) {
    logger.info("[Villager Inventory Plugin for WTHIT] Server side startup")
    registrar.entityData(VillagerInventoryProvider, Villager::class.java)
  }
}
