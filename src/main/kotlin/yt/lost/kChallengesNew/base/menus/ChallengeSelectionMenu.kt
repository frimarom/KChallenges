package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.base.challenges.ChallengeCollection

class ChallengeSelectionMenu(private var game: Game, private var challengeCollection: ChallengeCollection) : Listener {
    var challengeInventory: Inventory = Bukkit.createInventory(null, 54, "Challenges")

    init {
        for(challenge in challengeCollection.challenges){
            this.challengeInventory.addItem(challenge.characterizedItem)
        }
        for (i in 45..52){
            challengeInventory.setItem(i, ItemStack(Material.GRAY_STAINED_GLASS_PANE))
        }
        challengeInventory.setItem(53, createGuiItem(Material.GREEN_WOOL, "Weiter"))
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent){
        if(event.inventory != this.challengeInventory)
            return

        event.isCancelled = true
        val clicker = event.whoClicked as Player

        val item: ItemStack? = event.currentItem
        if(item?.type != Material.GREEN_WOOL && item?.type != Material.GRAY_STAINED_GLASS_PANE) {
            for (challenge in challengeCollection.challenges) {
                if (challenge.characterizedItem == item) {
                    challenge.isEnabled = !challenge.isEnabled

                    this.challengeInventory.setItem(event.slot, challenge.characterizedItem)
                }
            }
        }else{
            clicker.openInventory(game.settingsSelectionMenu.settingsInventory)
        }
    }

    @EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent){
        if(event.inventory == this.challengeInventory){
            HandlerList.unregisterAll(this)
        }
    }

    private fun createGuiItem(material: Material, name: String, vararg lore: String?): ItemStack {
        val item = ItemStack(material, 1)
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = lore.toMutableList()
        item.itemMeta = meta

        return item
    }
}