package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator

class GameSelectionMenu(
    private val progressiveGameCreator: ProgressiveGameCreator,
    gamePreparation: GamePreparation,
) : SelectionMenu(
        gamePreparation,
    ),
    Listener {
    override val inventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "GamemodeSelection")

    init {
        for (i in 0..26) {
            inventory.setItem(i, ItemStack(Material.GRAY_STAINED_GLASS_PANE))
        }
        inventory.setItem(
            11,
            createGuiItem(
                Material.GOLD_BLOCK,
                "${ChatColor.GOLD}Team-Gamemodes",
                "${ChatColor.GRAY}Spiele verschiedene Spiele wie ${ChatColor.GREEN}Achievement Battle${ChatColor.GRAY} als Teams gegeneinander",
            ),
        )
        inventory.setItem(
            15,
            createGuiItem(
                Material.NETHERITE_PICKAXE,
                "${ChatColor.GOLD}Challenges",
                "${ChatColor.GRAY}Versuche Minecraft mit einer bestimmten",
                "${ChatColor.GRAY}Einschränkung durchzuspielen",
            ),
        )
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory != this.inventory) {
            return
        }

        event.isCancelled = true

        val item = event.currentItem

        when (item?.type) {
            Material.GOLD_BLOCK -> {
                gamePreparation.settings.isChallenge = false
                progressiveGameCreator.nextStep(gamePreparation)
            }
            Material.NETHERITE_PICKAXE -> {
                gamePreparation.settings.isChallenge = true
                progressiveGameCreator.nextStep(gamePreparation)
            }
            else -> {}
        }
    }

    /*@EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent){
        if(event.inventory == this.inventory){
            HandlerList.unregisterAll(this)
        }
    }*/
}
