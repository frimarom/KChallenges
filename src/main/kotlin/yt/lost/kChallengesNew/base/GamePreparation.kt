package yt.lost.kChallengesNew.base

import org.bukkit.entity.Player
import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.base.teamgamemode.Team
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode
import yt.lost.kChallengesNew.settings.Settings

class GamePreparation(
    var admin: Player,
    var settings: Settings,
    var challenges: List<Challenge> = listOf(),
    var teamGameMode: TeamGameMode?,
    var teams: MutableList<Team> = mutableListOf(),
    var playerTeams: MutableMap<Player, Team> = mutableMapOf(),
)
