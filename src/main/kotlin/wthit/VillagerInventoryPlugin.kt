package dev.kpherox.vihp.wthit

import mcp.mobius.waila.api.ICommonRegistrar
import mcp.mobius.waila.api.IData
import mcp.mobius.waila.api.IWailaCommonPlugin
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.npc.Villager
import org.slf4j.LoggerFactory

class VillagerInventoryPlugin : IWailaCommonPlugin {
  companion object {
    private val INVENTORY_ID = ResourceLocation.fromNamespaceAndPath("vihp", "villager_inventory")

    val INVENTORY_TYPE: IData.Type<InventoryData> = IData.createType(INVENTORY_ID)
  }

  private val logger = LoggerFactory.getLogger("villager-inventory-hywla-plugin")

  override fun register(registrar: ICommonRegistrar) {
    logger.info("[Villager Inventory Plugin for WTHIT] Server side startup")
    registrar.dataType(INVENTORY_TYPE, InventoryData.CODEC)
    registrar.entityData(VillagerInventoryProvider, Villager::class.java)
  }
}
