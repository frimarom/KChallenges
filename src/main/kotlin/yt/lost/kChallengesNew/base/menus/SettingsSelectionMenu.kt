package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import yt.lost.kChallengesNew.base.GameCreatorDirection
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.settings.Settings

class SettingsSelectionMenu(
    private val progressiveGameCreator: ProgressiveGameCreator,
    gamePreparation: GamePreparation,
) : SelectionMenu(gamePreparation) {
    override var inventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Einstellungen")
    private val settings: Settings = gamePreparation.settings

    init {
        if (!gamePreparation.settings.isChallenge) {
            settings.teamAmount = 2
            settings.canDie = true
        }
        repeat(settings.settingsList.size) { i ->
            inventory.setItem(i, settings.settingsList[i].updateAndGetItem())
        }
        this.inventory.setItem(
            18,
            createGuiItem(
                Material.RED_WOOL,
                "Zurück",
            ),
        )
        this.inventory.setItem(
            26,
            createGuiItem(
                Material.GREEN_WOOL,
                "Starten",
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
        val clicker = event.whoClicked as Player

        if (event.isLeftClick) {
            when (item?.type) {
                Material.GREEN_WOOL -> {
                    gamePreparation.settings = settings
                    progressiveGameCreator.nextStep(gamePreparation, GameCreatorDirection.FORWARD)
                }
                Material.RED_WOOL -> {
                    progressiveGameCreator.nextStep(gamePreparation, GameCreatorDirection.BACKWARD)
                }
                else -> {
                    repeat(settings.settingsList.size) { i ->
                        val setting = settings.settingsList[i]
                        if (setting.updateAndGetItem().type == item?.type) {
                            setting.onLeftClick(clicker)
                            inventory.setItem(i, setting.updateAndGetItem())
                        }
                    }
                }
            }
        } else if (event.isRightClick) {
            repeat(settings.settingsList.size) { i ->
                val setting = settings.settingsList[i]
                if (setting.updateAndGetItem().type == item?.type) {
                    setting.onRightClick(clicker)
                    inventory.setItem(i, setting.updateAndGetItem())
                }
            }
        }
    }
}
