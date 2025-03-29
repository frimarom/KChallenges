package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameModeCollection

class TeamGameModeSelectionMenu(private val progressiveGameCreator: ProgressiveGameCreator,
                                private var teamGameModeCollection: TeamGameModeCollection,
                                gamePreparation: GamePreparation
): SelectionMenu(gamePreparation), Listener {
    override val inventory: Inventory = Bukkit.createInventory(null, InventoryType.PLAYER, "Team Game Modes")
}