package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.base.challenges.ChallengeCollection

class ChallengeSelectionMenu(
    private var progressiveGameCreator: ProgressiveGameCreator,
    gamePreparation: GamePreparation,
) : SelectionMenu(gamePreparation),
    Listener {
    override var inventory: Inventory = Bukkit.createInventory(null, 54, "Challenges")

    private val challengeCollection: ChallengeCollection = ChallengeCollection()

    init {
        for (challenge in challengeCollection.challenges) {
            this.inventory.addItem(challenge.updateAndGetCharacterizedItem())
        }
        for (i in 45..52) {
            inventory.setItem(i, ItemStack(Material.GRAY_STAINED_GLASS_PANE))
        }
        inventory.setItem(53, createGuiItem(Material.GREEN_WOOL, "Weiter"))
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory != this.inventory) {
            return
        }

        event.isCancelled = true
        val clicker = event.whoClicked as Player

        val item: ItemStack? = event.currentItem
        if (item?.type != Material.GREEN_WOOL && item?.type != Material.GRAY_STAINED_GLASS_PANE) {
            for (challenge in challengeCollection.challenges) {
                if (challenge.updateAndGetCharacterizedItem().type == item?.type) {
                    challenge.isEnabled = !challenge.isEnabled

                    this.inventory.setItem(event.slot, challenge.updateAndGetCharacterizedItem())
                }
            }
        } else {
            val enabledChallenges = challengeCollection.challenges.filter { it.isEnabled }
            gamePreparation.challenges = enabledChallenges
            progressiveGameCreator.nextStep(gamePreparation)
        }
    }

    /*@EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent){
        if(event.inventory == this.inventory){
            HandlerList.unregisterAll(this)
        }
    }*/
}
