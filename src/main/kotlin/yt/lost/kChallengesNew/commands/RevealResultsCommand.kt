package yt.lost.kChallengesNew.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import yt.lost.kChallengesNew.base.RunningTeamGame

class RevealResultsCommand(
    val runningTeamGame: RunningTeamGame,
) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
