package yt.lost.kChallengesNew

import org.bukkit.plugin.java.JavaPlugin
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.commands.ChallengeCommand

class KChallengesNew : JavaPlugin() {

    private lateinit var game: Game
    private lateinit var challengeCommand: ChallengeCommand


    override fun onEnable() {
        game = Game(this)
        challengeCommand = ChallengeCommand(this, game)
        this.getCommand("challenge")?.setExecutor(challengeCommand)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun registerAllChallenges() {

    }
}
