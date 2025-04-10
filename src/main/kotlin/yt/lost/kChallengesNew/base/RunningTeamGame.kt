package yt.lost.kChallengesNew.base

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameScoreboard
import yt.lost.kChallengesNew.commands.BackpackCommand
import yt.lost.kChallengesNew.commands.ProgressCommand
import yt.lost.kChallengesNew.commands.RevealResultsCommand
import yt.lost.kChallengesNew.settings.SettingsListener
import yt.lost.kChallengesNew.utils.Timer
import yt.lost.kChallengesNew.utils.TimerType

class RunningTeamGame(
    private val plugin: Plugin,
    private val gamePreparation: GamePreparation,
) : Game() {
    override val isRunning: Boolean
        get() = timer.running
    override val timer: Timer = Timer(TimerType.DOWNWARDS, plugin, this)

    private val settingsListener: SettingsListener
    private val activeTeamGameMode: TeamGameMode

    private val currentScoreboard: TeamGameScoreboard

    init {
        this.settings = gamePreparation.settings
        settingsListener = SettingsListener(this, settings)

        activeTeamGameMode = gamePreparation.teamGameMode!!
        currentScoreboard = TeamGameScoreboard(gamePreparation)

        (plugin as JavaPlugin).getCommand("backpack")?.setExecutor(BackpackCommand(this, gamePreparation))
        (plugin as JavaPlugin).getCommand("progress")?.setExecutor(ProgressCommand(activeTeamGameMode))
    }

    override fun start() {
        if (isRunning) {
            return
        }

        this.timer.start(settings.timerCountdown)

        plugin.server.pluginManager.registerEvents(settingsListener, plugin)

        for (player in Bukkit.getOnlinePlayers()) {
            player.sendTitle("${ChatColor.GREEN}Das Spiel", "${ChatColor.GRAY}wurde gestartet", 5, 40, 5)

            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

            player.foodLevel = 20
            player.health = player.getAttribute(Attribute.MAX_HEALTH)!!.baseValue

            player.inventory.clear()
        }
        plugin.server.pluginManager.registerEvents(activeTeamGameMode, plugin)
        activeTeamGameMode.onStart(gamePreparation, this)
    }

    override fun stop(cause: String) {
        // TODO idlelistener wieder adden
        // TODO spieler nach 3 sek tpen
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendTitle("${ChatColor.GOLD}Das Spiel", "${ChatColor.RED}ist vorbei", 5, 40, 5)
            player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f)
        }
        if (settings.manualResultReveal) {
            (plugin as JavaPlugin).getCommand("result")?.setExecutor(RevealResultsCommand(activeTeamGameMode, plugin))
            Bukkit.broadcastMessage(
                "${ChatColor.GRAY}Der Starter des Spiels muss jetzt ${ChatColor.GREEN}/reveal ${ChatColor.GRAY}machen damit die Ergebnisse angezeigt werden",
            )
        }
        object : BukkitRunnable() {
            override fun run() {
                val location = Bukkit.getWorld("world")?.spawnLocation
                for (player in Bukkit.getOnlinePlayers()) {
                    player.teleport(location!!)
                }
            }
        }.runTaskLater(plugin, 40)
        activeTeamGameMode.onStop()
    }
}
