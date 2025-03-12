package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
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

class GameSelectionMenu(private val game: Game, private val challengeCollection: ChallengeCollection): Listener {

    private var gameModeSelectionInventory: Inventory = Bukkit.createInventory(null, 54, "GamemodeSelection")







    private fun updateInventory(){

    }
}