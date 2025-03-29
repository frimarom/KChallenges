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
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.base.challenges.ChallengeCollection

class GameSelectionMenu(private val progressiveGameCreator: ProgressiveGameCreator,
                        gamePreparation: GamePreparation): SelectionMenu(
    gamePreparation
), Listener {

    override val inventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "GamemodeSelection")

    init {
        for(i in 0 .. 26){
            inventory.setItem(i, ItemStack(Material.GRAY_STAINED_GLASS_PANE))
        }
        inventory.setItem(11, createGuiItem(Material.GOLD_BLOCK, "${ChatColor.GOLD}Team-Gamemodes", "Stay tuned!\n${ChatColor.GRAY}Spiele Spiele wie Achievement Hunt etc"))
        inventory.setItem(15, createGuiItem(Material.NETHERITE_PICKAXE, "${ChatColor.GOLD}Challenges", "${ChatColor.GRAY}Versuche Minecraft mit einer bestimmten \n" +
                                                                                                                                          "Einschränkung durchzuspielen"))
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(event.inventory != this.inventory)
            return

        event.isCancelled = true

        val item = event.currentItem

        when (item?.type) {
            Material.GOLD_BLOCK -> {
                gamePreparation.isChallenge = false
                progressiveGameCreator.nextStep(gamePreparation)
            }
            Material.NETHERITE_PICKAXE -> {
                gamePreparation.isChallenge = true
                progressiveGameCreator.nextStep(gamePreparation)
            }
            else -> {}
        }
    }

    @EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent){
        if(event.inventory == this.inventory){
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