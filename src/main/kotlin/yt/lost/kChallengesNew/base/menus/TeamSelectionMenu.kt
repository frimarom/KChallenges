package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation
import yt.lost.kChallengesNew.base.ProgressiveGameCreator
import yt.lost.kChallengesNew.base.teamgamemode.Team
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode

class TeamSelectionMenu(private val progressiveGameCreator: ProgressiveGameCreator,
                        gamePreparation: GamePreparation) : SelectionMenu(gamePreparation) {
    override val inventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Team Auswahl:")

    init {
        repeat(gamePreparation.settings.teamAmount){ i ->
            val dyeColor = DyeColor.entries[i]
            val material = Material.matchMaterial("${dyeColor.name}_BED")
            val chatColor = dyeColorToChatColor(dyeColor)
            val team = Team(chatColor, "$chatColor Team ${dyeColor.name}",i, material!!)
            gamePreparation.teams.add(team)
        }
        for (team in gamePreparation.teams) {
            inventory.setItem(team.invLocation, gamePreparation.teams[team.invLocation].updateAndGetCharacterizedItem())
        }
        this.inventory.setItem(26,
            createGuiItem(
                Material.GREEN_WOOL,
                "Starten"
            )
        )
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(event.inventory != this.inventory)
            return

        event.isCancelled = true
        val clicker = event.whoClicked as Player

        val item: ItemStack? = event.currentItem


        if(item?.type != Material.GREEN_WOOL && item?.type != Material.GRAY_STAINED_GLASS_PANE) {
            for(team in gamePreparation.teams){
                if(team.updateAndGetCharacterizedItem().type == item?.type){
                    val previousTeam = gamePreparation.playerTeams[clicker]
                    if(previousTeam != null) {
                        previousTeam.member.remove(clicker)
                        inventory.setItem(previousTeam.invLocation, previousTeam.updateAndGetCharacterizedItem())
                    }
                    team.member.add(clicker)
                    gamePreparation.playerTeams[clicker] = team
                    inventory.setItem(team.invLocation, team.updateAndGetCharacterizedItem())
                }
            }
        } else if(item.type == Material.GREEN_WOOL){
            if(clicker == gamePreparation.admin){
                progressiveGameCreator.nextStep(gamePreparation)
            }else{
                clicker.sendMessage("${ChatColor.RED}Nur der Admin kann die Challenge starten")
            }
        }
    }

    //ich glaub ich bring mich um
    private fun dyeColorToChatColor(dyeColor: DyeColor): ChatColor {
        return when (dyeColor) {
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
}