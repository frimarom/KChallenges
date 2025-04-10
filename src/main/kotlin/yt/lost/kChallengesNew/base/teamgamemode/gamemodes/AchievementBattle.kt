package yt.lost.kChallengesNew.base.teamgamemode.gamemodes

import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.advancement.Advancement
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.RunningTeamGame
import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.base.menus.PagedInventory
import yt.lost.kChallengesNew.base.teamgamemode.Team
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class AchievementBattle(
    private val plugin: Plugin,
) : TeamGameMode() {
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

        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage(
                "${ChatColor.GRAY}" +
                    "Ziel des Spiels ist es so viele verschiedene Achievements wie möglich für dein Team zu holen. " +
                    "Das Team mit den meisten Achievements gewinnt am Ende das Spiel",
            )
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

    override fun revealResult(plugin: Plugin) {
        // Just temporary. See PagedInventory
        val sorted = teamAchievements.toList().sortedByDescending { it.second.size }
        val ranking = mutableMapOf<Team, Int>()
        var currentPlace = 1
        var lastSize: Int = -1
        var index = 0

        for ((key, value) in sorted) {
            val size = value.size
            if (size != lastSize) {
                currentPlace = index + 1
                lastSize = size
            }
            ranking[key] = currentPlace
            index++
        }

        var counter = ranking.size - 1

        object : BukkitRunnable() {
            override fun run() {
                if (counter <= -1) {
                    this.cancel()
                }
                val currentTeam = ranking.keys.toList()[counter]

                val placementText = TextComponent("${ChatColor.GRAY}Platz ${ChatColor.GREEN}${ranking[currentTeam]}:  ")
                val hoverPart = TextComponent("[${currentTeam.color}${currentTeam.name}]")

                hoverPart.color = net.md_5.bungee.api.ChatColor.GREEN
                hoverPart.hoverEvent =
                    HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        Text("Mitglieder:\n" + currentTeam.member.map { it.name }.joinToString("\n")),
                    )
                placementText.addExtra(hoverPart)

                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendTitle(
                        "${ChatColor.GRAY}Platz ${ChatColor.GREEN}${ranking[currentTeam]}: ${currentTeam.color}${currentTeam.name}",
                        teamAchievements[currentTeam]?.size!!.toString(),
                        5,
                        30,
                        5,
                    )
                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
                    player.spigot().sendMessage(placementText)
                }
                counter -= 1
            }
        }.runTaskTimer(plugin, 1, 40)
    }

    override fun showProgress(player: Player) {
        val team = this.gamePreparation?.playerTeams?.get(player)
        val items =
            Bukkit.advancementIterator().asSequence().toList().filter { it.display != null }.map {
                if (!teamAchievements[team]?.map { ach -> ach.key }?.contains(it.key)!!) {
                    createGuiItem(
                        it.display?.icon?.type!!,
                        "${it.display?.type?.color!!}${it.display?.title!!}",
                        "${ChatColor.DARK_GRAY}${it.display?.description!!}",
                        "${ChatColor.RED}Noch nicht geschafft",
                    )
                } else {
                    createGuiItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "${it.display?.type?.color!!}${it.display?.title!!}",
                        "${ChatColor.DARK_GRAY}${it.display?.description!!}",
                        "${ChatColor.GREEN}Schon geschafft!",
                    )
                }
            }
        val pagedInventory =
            PagedInventory(
                player,
                "Achievement Liste:",
                items,
            )
        plugin.server.pluginManager.registerEvents(pagedInventory, plugin)
        pagedInventory.open()
    }

    @EventHandler
    fun onAchievementGrant(event: PlayerAdvancementDoneEvent) {
        val advancement = event.advancement

        if (advancement.key.toString().startsWith("minecraft:recipes/")) {
            return
        }
        // Adventure und Nether rausmachen
        val playerTeam = this.gamePreparation?.playerTeams?.get(event.player)
        if (!teamAchievements[playerTeam]?.contains(advancement)!!) {
            teamAchievements[playerTeam]?.add(advancement)
            playerTeam?.sendMessageToMember(
                "Der Spieler ${event.player.name} hat das Achievement ${advancement.display?.title} für dein Team erreicht",
            )
        }
    }

    private fun getChallengeDescriptionHoverEffectList(activeChallenges: List<Challenge>): TextComponent {
        val message = TextComponent("Mitglieder: ")
        return message
    }

    private fun String.capitalizeWords(): String = split("_").joinToString(" ") { it.replaceFirstChar { c -> c.uppercaseChar() } }
}
