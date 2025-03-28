package yt.lost.kChallengesNew.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.base.menus.ChallengeSelectionMenu
import yt.lost.kChallengesNew.base.menus.GameSelectionMenu

class ChallengeCommand(private var plugin: Plugin, private var game: Game): CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if(!game.isRunning){
            val player: Player = p0 as Player

            val gameSelectionMenu = game.gameSelectionMenu

            plugin.server.pluginManager.registerEvents(gameSelectionMenu, plugin)
            plugin.server.pluginManager.registerEvents(game.challengeSelectionMenu, plugin)
            plugin.server.pluginManager.registerEvents(game.settingsSelectionMenu, plugin)


            player.openInventory.close()
            player.openInventory(gameSelectionMenu.gameModeSelectionInventory)

        }else{
            p0.sendMessage("§cDu kannst während die Challenge läuft das Menu nicht öffnen")
        }
        return true
    }
}