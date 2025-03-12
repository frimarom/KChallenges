package yt.lost.kChallengesNew.base

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.base.challenges.ChallengeCollection
import yt.lost.kChallengesNew.base.menus.ChallengeSelectionMenu
import yt.lost.kChallengesNew.base.menus.GameSelectionMenu
import yt.lost.kChallengesNew.base.menus.SettingsSelectionMenu
import yt.lost.kChallengesNew.listener.IdleListener
import yt.lost.kChallengesNew.settings.Settings
import yt.lost.kChallengesNew.settings.SettingsListener
import yt.lost.kChallengesNew.utils.Timer
import yt.lost.kChallengesNew.utils.TimerType

class Game(private val plugin: Plugin) {


    //TODO use this for game stats etc
    val isRunning
        get() = timer.running
    private val challengeCollection: ChallengeCollection = ChallengeCollection(this)
    val challengeSelectionMenu: ChallengeSelectionMenu = ChallengeSelectionMenu(this, challengeCollection)
    val settingsSelectionMenu: SettingsSelectionMenu = SettingsSelectionMenu(this)
    private val timer: Timer = Timer(TimerType.UPWARDS, plugin)
    private lateinit var settingsListener: SettingsListener
    private var idleListener: IdleListener = IdleListener(this)

    init{
        plugin.server.pluginManager.registerEvents(idleListener, plugin)
    }

    fun start(settings: Settings){
        if(isRunning)
            return

        settingsListener = SettingsListener(this, settings)

        this.timer.start()

        plugin.server.pluginManager.registerEvents(settingsListener, plugin)
        HandlerList.unregisterAll(idleListener)

        for(challenge in challengeCollection.challenges)
            if(challenge.isEnabled)
                plugin.server.pluginManager.registerEvents(challenge, plugin)


        for(player in Bukkit.getOnlinePlayers()){
            player.sendTitle("§aDer Timer", "§7wurde gestartet", 5, 40, 5)

            player.sendMessage("§8Das Spiel wurde mit den Challenges §7${challengeCollection.challenges.stream().map { challenge -> if(challenge.isEnabled){challenge.name} }} §8gestartet")

            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 5f, 5f)

            player.foodLevel = 20
            player.health = 20.0

            player.inventory.clear()
        }

    }

    fun stop(cause: String){
        Bukkit.broadcastMessage("§8------------ §9Challenge§8 ------------\n" +
                "\n"+
                "§cDer Timer wurde gestoppt\n" +
                "§c$cause \n" +
                "\n"+
                "§7Ihr habt ${this.timer.getTimeAsString()} verschwendet\n" +
                "\n"+
                "§8---------------------------------")

        this.timer.stop()

        object : BukkitRunnable() {

            override fun run() {
                Bukkit.broadcastMessage("§7Der Admin der Challenge muss §a/restart§7 ausführen")
            }

        }.runTaskLater(plugin, 60)

        for(player in Bukkit.getOnlinePlayers())
            player.gameMode = GameMode.SPECTATOR

        for(challenge in challengeCollection.challenges)
            HandlerList.unregisterAll(challenge)

        HandlerList.unregisterAll(settingsListener)
    }
}