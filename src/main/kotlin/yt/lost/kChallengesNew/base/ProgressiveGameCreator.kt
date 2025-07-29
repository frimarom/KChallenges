package yt.lost.kChallengesNew.base

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import yt.lost.kChallengesNew.base.menus.ChallengeSelectionMenu
import yt.lost.kChallengesNew.base.menus.GameSelectionMenu
import yt.lost.kChallengesNew.base.menus.SettingsSelectionMenu
import yt.lost.kChallengesNew.base.menus.TeamGameModeSelectionMenu
import yt.lost.kChallengesNew.base.menus.TeamSelectionMenu
import yt.lost.kChallengesNew.listener.IdleListener

class ProgressiveGameCreator(
    var currentPlayer: Player?,
    private val plugin: Plugin,
) {
    var isCreating: Boolean = true

    private lateinit var gameSelectionMenu: GameSelectionMenu
    private lateinit var teamGameModeSelectionMenu: TeamGameModeSelectionMenu
    private lateinit var challengeSelectionMenu: ChallengeSelectionMenu
    private lateinit var settingsSelectionMenu: SettingsSelectionMenu
    private lateinit var teamSelectionMenu: TeamSelectionMenu

    var idleListener: IdleListener = IdleListener(this)

    private var step: Int = 0

    init {
        plugin.server.pluginManager.registerEvents(idleListener, plugin)
    }

    fun nextStep(
        gamePreparation: GamePreparation,
        direction: GameCreatorDirection?,
    ) {
        when (direction) {
            GameCreatorDirection.FORWARD -> {
                step += 1
            }
            GameCreatorDirection.BACKWARD -> {
                step -= 1
            }
            else -> {
                step = 0
            }
        }
        when (step) {
            0 -> {
                gameSelectionMenu = GameSelectionMenu(this, gamePreparation)
                plugin.server.pluginManager.registerEvents(gameSelectionMenu, plugin)
                currentPlayer?.openInventory(gameSelectionMenu.inventory)
            }
            1 -> {
                if (gamePreparation.settings.isChallenge) {
                    challengeSelectionMenu = ChallengeSelectionMenu(this, gamePreparation)
                    plugin.server.pluginManager.registerEvents(challengeSelectionMenu, plugin)
                    currentPlayer?.openInventory(challengeSelectionMenu.inventory)
                } else {
                    teamGameModeSelectionMenu = TeamGameModeSelectionMenu(plugin, this, gamePreparation)
                    plugin.server.pluginManager.registerEvents(teamGameModeSelectionMenu, plugin)
                    currentPlayer?.openInventory(teamGameModeSelectionMenu.inventory)
                }
            }
            2 -> {
                settingsSelectionMenu = SettingsSelectionMenu(this, gamePreparation)
                plugin.server.pluginManager.registerEvents(settingsSelectionMenu, plugin)
                currentPlayer?.openInventory(settingsSelectionMenu.inventory)
            }
            3 -> {
                if (gamePreparation.settings.isChallenge) {
                    start(gamePreparation)
                } else {
                    teamSelectionMenu = TeamSelectionMenu(this, plugin, gamePreparation)
                    plugin.server.pluginManager.registerEvents(teamSelectionMenu, plugin)
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.openInventory(teamSelectionMenu.inventory)
                    }
                }
            }
            4 -> {
                HandlerList.unregisterAll(teamSelectionMenu)
                start(gamePreparation)
            }
        }
    }

    fun start(gamePreparation: GamePreparation) {
        isCreating = false
        HandlerList.unregisterAll(idleListener)
        for (player in Bukkit.getOnlinePlayers()) {
            player.closeInventory()
        }
        if (gamePreparation.settings.isChallenge) {
            val challenge = RunningChallengeGame(plugin, gamePreparation)
            challenge.start()
        } else {
            val teamGame = RunningTeamGame(plugin, gamePreparation)
            teamGame.start()
        }
    }
}
