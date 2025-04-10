package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.RunningTeamGame

abstract class TeamGameMode : Listener {
    var gamePreparation: GamePreparation? = null
    var runningTeamGame: RunningTeamGame? = null

    abstract fun onStart(
        gamePreparation: GamePreparation,
        runningTeamGame: RunningTeamGame,
    )

    abstract fun onStop()

    abstract fun updateAndGetCharacterizedItem(): ItemStack

    abstract fun updateScoreboardForAllPlayers()

    abstract fun updateScoreboardForTeam()

    abstract fun revealResult(plugin: Plugin)

    abstract fun showProgress(player: Player)

    protected fun createGuiItem(
        material: Material,
        name: String,
        vararg lore: String?,
    ): ItemStack {
        val item = ItemStack(material, 1)
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = lore.toMutableList()
        item.itemMeta = meta

        return item
    }
}
