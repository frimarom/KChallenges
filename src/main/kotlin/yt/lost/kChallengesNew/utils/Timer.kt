package yt.lost.kChallengesNew.utils

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.Game

class Timer(
    timerType: TimerType,
    private val plugin: Plugin,
    private val game: Game,
) {
    var running: Boolean = false
    var currentTime: Int = 0

    private val runnable: BukkitRunnable =
        object : BukkitRunnable() {
            override fun run() {
                if (running) {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§7§l" + formatTime(currentTime)))
                    }

                    if (timerType == TimerType.UPWARDS) {
                        currentTime += 1
                    } else {
                        if (currentTime > 0) {
                            currentTime -= 1
                        } else {
                            game.stop("Der Timer ist vorbei!")
                            running = false
                        }
                    }
                } else {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§7Der Timer ist gerade pausiert"))
                    }
                }
            }
        }

    fun start() {
        this.running = true
        runnable.runTaskTimer(plugin, 1, 20)
    }

    fun start(time: Int) {
        currentTime = time
        this.running = true
        runnable.runTaskTimer(plugin, 1, 20)
    }

    fun stop() {
        this.running = false
    }

    fun getTimeAsString(): String = formatTime(this.currentTime)

    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
