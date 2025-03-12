package yt.lost.kChallengesNew.utils

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

class Timer(timerType: TimerType, private val plugin: Plugin) {

    var running: Boolean = false

    private var currentTime: Int = 0
    private val runnable: BukkitRunnable = object : BukkitRunnable(){
        override fun run() {
            if(running) {
                for (player in Bukkit.getOnlinePlayers())
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§7§l"+shortInteger(currentTime)))


                if(timerType == TimerType.UPWARDS)
                    currentTime += 1
                else
                    if(currentTime >= 0)
                        currentTime -= 1

            }else
                for(player in Bukkit.getOnlinePlayers())
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§7Der Timer ist gerade pausiert"))
        }
    }

    fun start(){
        this.running = true
        runnable.runTaskTimer(plugin, 1, 20)
    }

    fun stop(){
        this.running = false
    }

    fun getTimeAsString(): String{
        return shortInteger(this.currentTime)
    }

    private fun shortInteger(d: Int): String {
        var duration = d
        var string = ""
        var hours = 0
        var minutes = 0
        var seconds = 0

        if (duration / 60 / 60 / 24 >= 1) {
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60
            duration -= duration / 60 / 60 * 60 * 60
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60
            duration -= duration / 60 * 60
        }
        if (duration >= 1) seconds = duration
        string = if (hours <= 9) {
            string + "0" + hours + ":"
        } else {
            "$string$hours:"
        }
        string = if (minutes <= 9) {
            string + "0" + minutes + ":"
        } else {
            "$string$minutes:"
        }
        string = if (seconds <= 9) {
            string + "0" + seconds
        } else {
            string + seconds
        }
        return string
    }
}