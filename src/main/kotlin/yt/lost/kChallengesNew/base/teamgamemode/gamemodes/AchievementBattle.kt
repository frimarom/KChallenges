package yt.lost.kChallengesNew.base.teamgamemode.gamemodes

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.RunningTeamGame
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class AchievementBattle : TeamGameMode() {
    override fun onStart(
        gamePreparation: GamePreparation,
        runningTeamGame: RunningTeamGame,
    ) {
        this.gamePreparation = gamePreparation
        this.runningTeamGame = runningTeamGame
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun updateAndGetCharacterizedItem(): ItemStack = ItemStack(Material.BLUE_ORCHID)
}
