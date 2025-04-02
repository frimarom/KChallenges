package yt.lost.kChallengesNew.base

import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.commands.BackpackCommand
import yt.lost.kChallengesNew.settings.SettingsListener
import yt.lost.kChallengesNew.utils.Timer
import yt.lost.kChallengesNew.utils.TimerType

class RunningChallengeGame(
    private val plugin: Plugin,
    private val gamePreparation: GamePreparation,
) : Game() {
    override val isRunning: Boolean
        get() = timer.running
    override val timer: Timer = Timer(TimerType.UPWARDS, plugin, this)

    private var settingsListener: SettingsListener

    init {
        this.settings = gamePreparation.settings
        settingsListener = SettingsListener(this, gamePreparation)
        (plugin as JavaPlugin).getCommand("backpack")?.setExecutor(BackpackCommand(this, gamePreparation))
    }

    override fun start() {
        if (isRunning) {
            return
        }

        this.timer.start()

        plugin.server.pluginManager.registerEvents(settingsListener, plugin)

        val challengeText = TextComponent("Das Spiel wurde mit den Challenges").apply { color = net.md_5.bungee.api.ChatColor.GRAY }
        challengeText.addExtra(getChallengeDescriptionHoverEffectList(this.gamePreparation.challenges))
        challengeText.addExtra(TextComponent("gestartet").apply { color = net.md_5.bungee.api.ChatColor.GRAY })

        for (player in Bukkit.getOnlinePlayers()) {
            player.sendTitle("§aDer Timer", "§7wurde gestartet", 5, 40, 5)

            player.spigot().sendMessage(challengeText)

            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 5f, 5f)

            player.foodLevel = 20
            player.health = player.getAttribute(Attribute.MAX_HEALTH)!!.baseValue

            player.inventory.clear()
        }

        for (challenge in gamePreparation.challenges) {
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

        for (challenge in gamePreparation.challenges) {
            HandlerList.unregisterAll(challenge)
        }

        HandlerList.unregisterAll(settingsListener)
    }

    private fun getChallengeDescriptionHoverEffectList(activeChallenges: List<Challenge>): TextComponent {
        val message = TextComponent(" ")

        for (challenge in activeChallenges) {
            val hoverPart = TextComponent(challenge.name)

            hoverPart.color = net.md_5.bungee.api.ChatColor.GREEN
            hoverPart.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(challenge.description.joinToString(" ")))

            message.addExtra(hoverPart)
            message.addExtra(" ")
        }
        return message
    }
}
