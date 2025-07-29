package yt.lost.kChallengesNew

import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.plugin.java.JavaPlugin
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.commands.BackpackCommand
import yt.lost.kChallengesNew.commands.ChallengeCommand

class KChallengesNew : JavaPlugin() {
    private lateinit var game: Game
    private lateinit var challengeCommand: ChallengeCommand
    private lateinit var backpackCommand: BackpackCommand

    override fun onEnable() {
        challengeCommand = ChallengeCommand(this)

        this.getCommand("challenge")?.setExecutor(challengeCommand)

        for (player in Bukkit.getOnlinePlayers()) {
            player.getAttribute(Attribute.MAX_HEALTH)?.baseValue = 20.0
            player.health = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!!
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    /* Features
     * -pause challenge
     * */
}
