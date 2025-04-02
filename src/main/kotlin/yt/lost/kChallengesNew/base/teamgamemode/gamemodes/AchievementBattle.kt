package yt.lost.kChallengesNew.base.teamgamemode.gamemodes

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.advancement.Advancement
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.RunningTeamGame
import yt.lost.kChallengesNew.base.teamgamemode.Team
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class AchievementBattle : TeamGameMode() {
    private var teamAchievements: MutableMap<Team, MutableList<Advancement>> = mutableMapOf()

    override fun onStart(
        gamePreparation: GamePreparation,
        runningTeamGame: RunningTeamGame,
    ) {
        this.gamePreparation = gamePreparation
        this.runningTeamGame = runningTeamGame

        for (team in gamePreparation.teams) {
            teamAchievements[team] = mutableListOf()
        }
    }

    override fun onStop() {
        val bestTeam = teamAchievements.maxByOrNull { it.value.size }?.key
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage("Das Team ${bestTeam?.name} hat gewonnen")
        }
    }

    override fun updateAndGetCharacterizedItem(): ItemStack =
        createGuiItem(
            Material.ENCHANTED_GOLDEN_APPLE,
            "${ChatColor.GREEN}Achievement Battle",
            "${ChatColor.GRAY}Welches Team sammelt die meisten Achievements?",
        )

    override fun updateScoreboardForAllPlayers() {
        TODO("Not yet implemented")
    }

    override fun updateScoreboardForTeam() {
        TODO("Not yet implemented")
    }

    @EventHandler
    fun onAchievementGrant(event: PlayerAdvancementDoneEvent) {
        val advancement = event.advancement

        if (advancement.key.toString().startsWith("minecraft:recipes/")) {
            return
        }
        // Adventure und Nether rausmachen
        // ersten 10 min pvp aus
        // Settings sterben erlaubt
        val playerTeam = this.gamePreparation?.playerTeams?.get(event.player)
        if (!teamAchievements[playerTeam]?.contains(advancement)!!) {
            teamAchievements[playerTeam]?.add(advancement)
            playerTeam?.sendMessageToMember(
                "Der Spieler ${event.player.name} hat das Achievement ${advancement.display?.title} für dein Team erreicht",
            )
        }
    }

    private fun getAchievementName(advancement: Advancement): String =
        advancement.key.key
            .replace("_", " ")
            .capitalizeWords()

    private fun String.capitalizeWords(): String = split("_").joinToString(" ") { it.replaceFirstChar { c -> c.uppercaseChar() } }
}
