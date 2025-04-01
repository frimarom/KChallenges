package yt.lost.kChallengesNew.base.teamgamemode

import yt.lost.kChallengesNew.base.teamgamemode.gamemodes.AchievementBattle

class TeamGameModeCollection {
    val teamGameModes: MutableList<TeamGameMode> = ArrayList()

    init {
        teamGameModes.add(AchievementBattle())
    }
}
