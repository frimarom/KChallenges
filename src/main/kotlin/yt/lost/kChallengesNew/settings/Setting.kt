package yt.lost.kChallengesNew.settings

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Setting(
    private val operation: () -> ItemStack,
    private val leftClickAction: (player: Player) -> Unit,
    private val rightClickAction: (player: Player) -> Unit,
) {
    fun updateAndGetItem(): ItemStack = operation()

    fun onLeftClick(player: Player) {
        leftClickAction(player)
    }

    fun onRightClick(player: Player) {
        rightClickAction(player)
    }
}
