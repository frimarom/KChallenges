package yt.lost.kChallengesNew.base

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.settings.Settings
import yt.lost.kChallengesNew.settings.SettingsListener
import yt.lost.kChallengesNew.utils.Timer
import yt.lost.kChallengesNew.utils.TimerType

class RunningChallengeGame(
    private val plugin: Plugin,
    private val activeChallenges: List<Challenge>,
    settings: Settings,
) : Game() {
    override val isRunning: Boolean
        get() = timer.running
    override val timer: Timer = Timer(TimerType.UPWARDS, plugin)

    private var settingsListener: SettingsListener

    init {
        this.settings = settings
        settingsListener = SettingsListener(this, settings)
    }

    override fun start() {
        if (isRunning) {
            return
        }

        this.timer.start()

        plugin.server.pluginManager.registerEvents(settingsListener, plugin)

        for (player in Bukkit.getOnlinePlayers()) {
            player.sendTitle("§aDer Timer", "§7wurde gestartet", 5, 40, 5)

            player.sendMessage("§8Das Spiel wurde mit den Challenges §7${activeChallenges.map { it.name }} §8gestartet")

            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 5f, 5f)

            player.foodLevel = 20
            player.health = player.getAttribute(Attribute.MAX_HEALTH)!!.baseValue

            player.inventory.clear()
        }

        for (challenge in activeChallenges) {
            challenge.game = this
            plugin.server.pluginManager.registerEvents(challenge, plugin)
            challenge.onStart()
        }
    }

    override fun stop(cause: String) {
        Bukkit.broadcastMessage(
            "§8------------ §9Challenge§8 ------------\n" +
                "\n" +
                "§cDer Timer wurde gestoppt\n" +
                "§c$cause \n" +
                "\n" +
                "§7Ihr habt ${this.timer.getTimeAsString()} verschwendet\n" +
                "\n" +
                "§8---------------------------------",
        )

        this.timer.stop()

        object : BukkitRunnable() {
            override fun run() {
                Bukkit.broadcastMessage("§7Der Admin der Challenge muss §a/restart§7 ausführen")
            }
        }.runTaskLater(plugin, 60)

        for (player in Bukkit.getOnlinePlayers()) {
            player.gameMode = GameMode.SPECTATOR
        }

        for (challenge in activeChallenges) {
            HandlerList.unregisterAll(challenge)
        }

        HandlerList.unregisterAll(settingsListener)
    }
}
