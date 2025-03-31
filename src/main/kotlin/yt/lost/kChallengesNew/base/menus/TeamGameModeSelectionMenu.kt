package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameModeCollection

class TeamGameModeSelectionMenu(private val progressiveGameCreator: ProgressiveGameCreator,
                                gamePreparation: GamePreparation
): SelectionMenu(gamePreparation) {
    override val inventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Team Game Modes")

    private val teamGameModeCollection: TeamGameModeCollection = TeamGameModeCollection()

    init {
        for(teamGameMode in teamGameModeCollection.teamGameModes){
            this.inventory.addItem(teamGameMode.updateAndGetCharacterizedItem())
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(event.inventory != this.inventory)
            return

        event.isCancelled = true

        val item: ItemStack? = event.currentItem

        for (teamGameMode in teamGameModeCollection.teamGameModes) {
            if (teamGameMode.updateAndGetCharacterizedItem().type == item?.type) {
                gamePreparation.teamGameMode = teamGameMode
                progressiveGameCreator.nextStep(gamePreparation)
            }
        }
    }
}