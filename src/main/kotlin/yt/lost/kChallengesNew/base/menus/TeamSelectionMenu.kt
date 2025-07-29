package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import yt.lost.kChallengesNew.base.GameCreatorDirection
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.base.teamgamemode.Team

class TeamSelectionMenu(
    private val progressiveGameCreator: ProgressiveGameCreator,
    private val plugin: Plugin,
    gamePreparation: GamePreparation,
) : SelectionMenu(gamePreparation) {
    override val inventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Team Auswahl:")

    // TODO Team Creation und autofill und so auslagern in neue Klasse
    init {
        val colors = DyeColor.entries.shuffled()
        repeat(gamePreparation.settings.teamAmount) { i ->
            val dyeColor = colors[i]
            val material = Material.matchMaterial("${dyeColor.name}_BED")
            val chatColor = dyeColorToChatColor(dyeColor)
            val team = Team(chatColor, "${chatColor}Team ${dyeColor.name}", i, material!!)
            gamePreparation.teams.add(team)
        }
        for (team in gamePreparation.teams) {
            inventory.setItem(team.invLocation, gamePreparation.teams[team.invLocation].updateAndGetCharacterizedItem())
        }
        this.inventory.setItem(
            26,
            createGuiItem(
                Material.GREEN_WOOL,
                "${ChatColor.GREEN}Starten",
            ),
        )
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory != this.inventory) {
            return
        }

        event.isCancelled = true
        val clicker = event.whoClicked as Player

        val item: ItemStack? = event.currentItem

        if (item?.type != Material.GREEN_WOOL && item?.type != Material.GRAY_STAINED_GLASS_PANE) {
            for (team in gamePreparation.teams) {
                if (team.updateAndGetCharacterizedItem().type == item?.type) {
                    addPlayerToTeam(clicker, team)
                }
            }
        } else if (item.type == Material.GREEN_WOOL) {
            if (clicker == gamePreparation.admin) {
                autofillRemainingPlayer()
                progressiveGameCreator.nextStep(gamePreparation, GameCreatorDirection.FORWARD)
            } else {
                clicker.sendMessage("${ChatColor.RED}Warte bis der Admin das Spiel startet!")
            }
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory != this.inventory) {
            return
        }

        object : BukkitRunnable() {
            override fun run() {
                event.player.openInventory(inventory)
            }
        }.runTaskLater(plugin, 1)
    }

    private fun addPlayerToTeam(
        player: Player,
        team: Team,
    ) {
        val previousTeam = gamePreparation.playerTeams[player]
        if (previousTeam != null) {
            previousTeam.member.remove(player)
            inventory.setItem(previousTeam.invLocation, previousTeam.updateAndGetCharacterizedItem())
        }
        team.member.add(player)
        gamePreparation.playerTeams[player] = team
        inventory.setItem(team.invLocation, team.updateAndGetCharacterizedItem())
    }

    private fun autofillRemainingPlayer() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (gamePreparation.playerTeams[player] != null) {
                continue
            }
            val sortedTeams = gamePreparation.teams.sortedBy { it.member.size }
            val lowestTeam = sortedTeams[0]
            addPlayerToTeam(player, lowestTeam)
            player.sendMessage(
                "${ChatColor.GRAY}Du wurdest dem Team ${lowestTeam.color}" +
                    "${lowestTeam.name}${ChatColor.GRAY} automatisch hinzugefügt",
            )
        }
    }

    // ich glaub ich bring mich um
    private fun dyeColorToChatColor(dyeColor: DyeColor): ChatColor =
        when (dyeColor) {
            DyeColor.WHITE -> ChatColor.WHITE
            DyeColor.ORANGE -> ChatColor.GOLD
            DyeColor.MAGENTA -> ChatColor.LIGHT_PURPLE
            DyeColor.LIGHT_BLUE -> ChatColor.AQUA
            DyeColor.YELLOW -> ChatColor.YELLOW
            DyeColor.LIME -> ChatColor.GREEN
            DyeColor.PINK -> ChatColor.LIGHT_PURPLE
            DyeColor.GRAY -> ChatColor.DARK_GRAY
            DyeColor.LIGHT_GRAY -> ChatColor.GRAY
            DyeColor.CYAN -> ChatColor.DARK_AQUA
            DyeColor.PURPLE -> ChatColor.DARK_PURPLE
            DyeColor.BLUE -> ChatColor.DARK_BLUE
            DyeColor.BROWN -> ChatColor.GOLD
            DyeColor.GREEN -> ChatColor.DARK_GREEN
            DyeColor.RED -> ChatColor.DARK_RED
            DyeColor.BLACK -> ChatColor.BLACK
        }
}
