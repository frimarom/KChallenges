package yt.lost.kChallengesNew.base

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import yt.lost.kChallengesNew.base.challenges.ChallengeCollection
import yt.lost.kChallengesNew.base.menus.ChallengeSelectionMenu
import yt.lost.kChallengesNew.base.menus.GameSelectionMenu
import yt.lost.kChallengesNew.base.menus.SettingsSelectionMenu
import yt.lost.kChallengesNew.base.menus.TeamGameModeSelectionMenu
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameModeCollection
import yt.lost.kChallengesNew.commands.BackpackCommand
import yt.lost.kChallengesNew.listener.IdleListener

class ProgressiveGameCreator(var currentPlayer: Player?, private val plugin: Plugin) {

    var isCreating: Boolean = true
    var isInProgression: Boolean = false

    private val challengeCollection: ChallengeCollection = ChallengeCollection()
    private val teamGameModeCollection: TeamGameModeCollection = TeamGameModeCollection()

    private lateinit var gameSelectionMenu: GameSelectionMenu
    private lateinit var teamGameModeSelectionMenu: TeamGameModeSelectionMenu
    private lateinit var challengeSelectionMenu: ChallengeSelectionMenu
    private lateinit var settingsSelectionMenu: SettingsSelectionMenu

    var idleListener: IdleListener = IdleListener(this)

    private var step: Int = 0

    init {
        plugin.server.pluginManager.registerEvents(idleListener, plugin)
    }

    fun nextStep(gamePreparation: GamePreparation){
        when(step){
            0 ->{
                gameSelectionMenu = GameSelectionMenu(this, gamePreparation)
                plugin.server.pluginManager.registerEvents(gameSelectionMenu, plugin)
                step += 1
                currentPlayer?.openInventory(gameSelectionMenu.inventory)
            }
            1 ->{
                teamGameModeSelectionMenu = TeamGameModeSelectionMenu(this, teamGameModeCollection, gamePreparation)
                challengeSelectionMenu = ChallengeSelectionMenu(this, challengeCollection, gamePreparation)
                plugin.server.pluginManager.registerEvents(challengeSelectionMenu, plugin)
                plugin.server.pluginManager.registerEvents(teamGameModeSelectionMenu, plugin)
                step += 1
                if(gamePreparation.isChallenge)
                    currentPlayer?.openInventory(challengeSelectionMenu.inventory)
                else
                    currentPlayer?.openInventory(teamGameModeSelectionMenu.inventory)
            }
            2 ->{
                settingsSelectionMenu = SettingsSelectionMenu(this, gamePreparation)
                plugin.server.pluginManager.registerEvents(settingsSelectionMenu, plugin)
                step += 1
                currentPlayer?.openInventory(settingsSelectionMenu.inventory)
            }
            3 ->{
                start(gamePreparation)
            }
        }
    }

    fun start(gamePreparation: GamePreparation){
        isCreating = false
        if(gamePreparation.isChallenge){
            var challenge: RunningChallengeGame = RunningChallengeGame(plugin, gamePreparation.challenges, gamePreparation.settings)
            (plugin as JavaPlugin).getCommand("backpack")?.setExecutor(BackpackCommand(challenge))
            challenge.start()
        }
    }
}