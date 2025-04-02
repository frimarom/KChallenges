package yt.lost.kChallengesNew.base

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameScoreboard
import yt.lost.kChallengesNew.commands.BackpackCommand
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
        activeTeamGameMode = gamePreparation.teamGameMode!!
        settingsListener = SettingsListener(this, gamePreparation)
        currentScoreboard = TeamGameScoreboard(gamePreparation)
        (plugin as JavaPlugin).getCommand("backpack")?.setExecutor(BackpackCommand(this, gamePreparation))
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
        activeTeamGameMode.onStop()
    }
}
