package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
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
}
