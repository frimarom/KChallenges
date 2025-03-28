package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameModeCollection

class TeamGameModeSelectionMenu(private val game: Game, private var teamGameModeCollection: TeamGameModeCollection): Listener {
    val selectionInventory: Inventory = Bukkit.createInventory(null, InventoryType.PLAYER, "Team Game Modes")
}