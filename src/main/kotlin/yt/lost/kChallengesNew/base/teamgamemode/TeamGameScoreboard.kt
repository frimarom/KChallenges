package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team
import yt.lost.kChallengesNew.base.GamePreparation

class TeamGameScoreboard(
    gamePreparation: GamePreparation,
) {
    val scoreboard: Scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard

    init {
        for (team in gamePreparation.teams) {
            val roleAdmin: Team = scoreboard.registerNewTeam(team.name)
            roleAdmin.displayName = "${team.color}${team.name}${ChatColor.DARK_GRAY} | "
            roleAdmin.color = team.color
            roleAdmin.prefix = "${team.color}${team.name}${ChatColor.DARK_GRAY} | "
            for (member in team.member) {
                roleAdmin.addEntry(member.name)
            }
        }
        for (player in Bukkit.getOnlinePlayers()) {
            player.scoreboard = scoreboard
        }
    }
}
