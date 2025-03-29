package yt.lost.kChallengesNew

import org.bukkit.plugin.java.JavaPlugin
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.commands.BackpackCommand
import yt.lost.kChallengesNew.commands.ChallengeCommand
import yt.lost.kChallengesNew.utils.Backpack

class KChallengesNew : JavaPlugin() {

    private lateinit var game: Game
    private lateinit var challengeCommand: ChallengeCommand
    private lateinit var backpackCommand: BackpackCommand

    override fun onEnable() {
        challengeCommand = ChallengeCommand(this)

        this.getCommand("challenge")?.setExecutor(challengeCommand)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun registerAllChallenges() {

    }
}
