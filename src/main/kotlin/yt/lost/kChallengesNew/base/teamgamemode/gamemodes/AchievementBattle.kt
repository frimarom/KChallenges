package yt.lost.kChallengesNew.base.teamgamemode.gamemodes

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class AchievementBattle: TeamGameMode() {
    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun updateAndGetCharacterizedItem(): ItemStack {
        return ItemStack(Material.BLUE_ORCHID)
    }
}