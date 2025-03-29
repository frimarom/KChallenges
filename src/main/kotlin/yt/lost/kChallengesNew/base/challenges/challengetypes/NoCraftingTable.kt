package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.challenges.Challenge

class NoCraftingTable: Challenge() {
    override val name: String = "No CraftingTable"
    override var description: String = "Man darf keinen Crafting Table benutzen"

    @EventHandler
    fun onCrafting(event: CraftItemEvent){
        if(event.clickedInventory!!.type == org.bukkit.event.inventory.InventoryType.CRAFTING)
            game?.stop("${(event.whoClicked as Player).name}hat etwas gecraftet")
    }

    override fun updateAndGetCharacterizedItem(): ItemStack {
        return createGuiItem(
            Material.CRAFTING_TABLE,
            "§a$name",
            "§7$description",
            "§7Status: " + if (this.isEnabled) "§aAn" else "§cAus"
        )
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}