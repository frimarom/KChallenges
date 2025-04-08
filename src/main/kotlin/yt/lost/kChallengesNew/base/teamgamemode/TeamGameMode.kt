package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.RunningTeamGame

abstract class TeamGameMode : Listener {
    val participatingTeams: MutableList<Team> = mutableListOf()
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
