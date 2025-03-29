package yt.lost.kChallengesNew.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.utils.Backpack

class BackpackCommand(private val game: Game): CommandExecutor {

    private val backpack: Backpack = Backpack(null)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(!game.isRunning){
            return false
        }
        if(!game.settings.backpack){
            sender.sendMessage("${ChatColor.RED}Das Backpack ist zurzeit ausgeschaltet")
            return false
        }
        (sender as Player).openInventory(backpack.backpack)
        return true
    }
}