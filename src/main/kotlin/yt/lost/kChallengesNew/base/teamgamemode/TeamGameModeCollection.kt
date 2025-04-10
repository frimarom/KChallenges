package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.teamgamemode.gamemodes.AchievementBattle

class TeamGameModeCollection(
    plugin: Plugin,
) {
    val teamGameModes: MutableList<TeamGameMode> = ArrayList()

    init {
        teamGameModes.add(AchievementBattle(plugin))
    }
}
