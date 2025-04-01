package yt.lost.kChallengesNew.base

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameScoreboard
import yt.lost.kChallengesNew.settings.SettingsListener
import yt.lost.kChallengesNew.utils.Timer
import yt.lost.kChallengesNew.utils.TimerType

class RunningTeamGame(
    private val plugin: Plugin,
    private val gamePreparation: GamePreparation,
) : Game() {
    override val isRunning: Boolean
        get() = timer.running
    override val timer: Timer = Timer(TimerType.DOWNWARDS, plugin)

    private val settingsListener: SettingsListener
    private val activeTeamGameMode: TeamGameMode

    private val currentScoreboard: TeamGameScoreboard

    init {
        this.settings = gamePreparation.settings
        activeTeamGameMode = gamePreparation.teamGameMode!!
        settingsListener = SettingsListener(this, settings)
        currentScoreboard = TeamGameScoreboard(gamePreparation)
    }

    override fun start() {
        if (isRunning) {
            return
        }

        this.timer.start(settings.timerCountdown)

        plugin.server.pluginManager.registerEvents(settingsListener, plugin)

        for (player in Bukkit.getOnlinePlayers()) {
            player.sendTitle("§aDer Timer", "§7wurde gestartet", 5, 40, 5)

            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

            player.foodLevel = 20
            player.health = player.getAttribute(Attribute.MAX_HEALTH)!!.baseValue

            player.inventory.clear()
        }

        plugin.server.pluginManager.registerEvents(activeTeamGameMode, plugin)
        activeTeamGameMode.onStart(gamePreparation, this)
    }

    override fun stop(cause: String) {
        TODO("Not yet implemented")
    }
}
