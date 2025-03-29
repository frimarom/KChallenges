package yt.lost.kChallengesNew.base.menus

import org.bukkit.inventory.Inventory
import yt.lost.kChallengesNew.base.GamePreparation

abstract class SelectionMenu(protected val gamePreparation: GamePreparation) {
    abstract val inventory: Inventory
}