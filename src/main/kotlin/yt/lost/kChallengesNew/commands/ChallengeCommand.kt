package yt.lost.kChallengesNew.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.settings.Settings

class ChallengeCommand(
    plugin: Plugin,
) : CommandExecutor {
    private var progressiveGameCreator: ProgressiveGameCreator = ProgressiveGameCreator(null, plugin)

    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>,
    ): Boolean {
        // TODO maybe beim neuöffnen des commands wieder von vorne beginnen oder "zurück" tasten einbauen
        if (progressiveGameCreator.currentPlayer != p0 && progressiveGameCreator.currentPlayer != null) {
            if (progressiveGameCreator.isCreating) {
                p0.sendMessage("${ChatColor.RED}Du kannst nicht während ein anderer bereits startet die Modi auswählen")
                return false
            } else {
                progressiveGameCreator.currentPlayer = p0 as Player
            }
        } else {
            if (progressiveGameCreator.isCreating) {
                progressiveGameCreator.currentPlayer = p0 as Player
                progressiveGameCreator.nextStep(GamePreparation(p0, Settings(), listOf(), null), null)
            } else {
                p0.sendMessage("${ChatColor.RED}Du kannst während die Challenge läuft das Menu nicht öffnen")
            }
        }
        return true
    }
}
