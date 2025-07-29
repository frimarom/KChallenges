package yt.lost.kChallengesNew.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class PauseGameCommand : CommandExecutor {
    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String?>,
    ): Boolean = true
}
