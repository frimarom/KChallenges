package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.CraftItemEvent
import yt.lost.kChallengesNew.base.challenges.Challenge

class NoCraftingTable : Challenge() {
    init {
        material = Material.CRAFTING_TABLE
        name = "No CraftingTable"
        description = listOf("Man darf keinen Crafting Table benutzen")
    }

    @EventHandler
    fun onCrafting(event: CraftItemEvent) {
        if (event.clickedInventory!!.type == org.bukkit.event.inventory.InventoryType.CRAFTING) {
            game?.stop("${(event.whoClicked as Player).name}hat etwas gecraftet")
        }
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}
