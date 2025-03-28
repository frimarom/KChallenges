package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.ChatColor
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

class GameSelectionMenu(private val game: Game): Listener {

    val gameModeSelectionInventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "GamemodeSelection")

    init {
        for(i in 0 .. 27){
            gameModeSelectionInventory.setItem(i, ItemStack(Material.GRAY_STAINED_GLASS_PANE))
        }
        gameModeSelectionInventory.setItem(11, createGuiItem(Material.GOLD_BLOCK, "${ChatColor.GOLD}Team-Gamemodes", "Stay tuned!\n${ChatColor.GRAY}Spiele Spiele wie Achievement Hunt etc"))
        gameModeSelectionInventory.setItem(15, createGuiItem(Material.NETHERITE_PICKAXE, "${ChatColor.GOLD}Challenges", "${ChatColor.GRAY}Versuche Minecraft mit einer bestimmten \n" +
                                                                                                                                          "Einschränkung durchzuspielen"))
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(event.inventory != this.gameModeSelectionInventory)
            return


        event.isCancelled = true

        val item = event.currentItem
        val clicker = event.whoClicked as Player

        when (item?.type) {
            Material.GOLD_BLOCK -> {
                clicker.openInventory(game.teamGameModeSelectionMenu.selectionInventory)
            }
            Material.NETHERITE_PICKAXE -> {
                clicker.openInventory(game.challengeSelectionMenu.challengeInventory)
            }
            else -> {}
        }
    }

    @EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent){
        if(event.inventory == this.gameModeSelectionInventory){
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