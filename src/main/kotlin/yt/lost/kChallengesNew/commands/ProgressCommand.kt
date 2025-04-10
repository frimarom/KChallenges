package yt.lost.kChallengesNew.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class ProgressCommand(
    private var teamGameMode: TeamGameMode,
) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        teamGameMode.showProgress(sender as Player)
        return true
    }
}
