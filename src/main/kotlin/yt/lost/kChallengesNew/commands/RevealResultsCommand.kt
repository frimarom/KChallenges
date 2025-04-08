package yt.lost.kChallengesNew.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class RevealResultsCommand(
    private val activeTeamGameMode: TeamGameMode,
    private val plugin: Plugin,
) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        if (!activeTeamGameMode.gamePreparation?.settings?.manualResultReveal!!) {
            sender.sendMessage("${ChatColor.RED}Die Manuelle Ergebnissanzeige ist ausgestellt")
            return false
        }
        if (sender == activeTeamGameMode.gamePreparation?.admin) {
            activeTeamGameMode.revealResult(plugin)
        } else {
            sender.sendMessage("${ChatColor.RED}Nur der Admin kann die Ergebnisse anzeigen")
            return false
        }
        return true
    }
}
