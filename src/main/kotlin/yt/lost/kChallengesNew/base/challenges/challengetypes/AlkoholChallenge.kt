package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityMountEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.challenges.Challenge
import java.util.*

class AlkoholChallenge : Challenge() {
    /*
     * SchildBlock
     * BlazeKill
     * EndermanKill
     * Dia abbauen
     * Tot
     * Random Block List to Avoid
     *
     * */
    override val name: String = "Alkohol Challenge"
    override val description: String = "Für verschiedene "

    private var mustDrink: Boolean = false
    private var drinker: Player? = null
    private var continuingItem: ItemStack =
        createGuiItem(
            Material.HONEY_BOTTLE,
            "ICH HAB GETRUNKEN",
            "Rechtsklick auf dieses Item wenn du getrunken hast damit du dich wieder bewegen kannst",
        )
    private var forbiddenBlocks: List<Material> = ArrayList()

    override fun onStart() {
        Bukkit.getOnlinePlayers().forEach { player -> player.inventory.setItemInOffHand(continuingItem) }
        val list = Material.entries.filter { it.isBlock }.filter { it.isItem }
        forbiddenBlocks = list.shuffled().take(40)
    }

    @EventHandler
    fun onEntityMove(event: PlayerMoveEvent) {
        if (!mustDrink) {
            return
        }

        val from: Location = event.from
        val to: Location? = event.to

        if (to != null && (from.x != to.x || from.z != to.z || from.y != to.y)) {
            event.setTo(Location(from.getWorld(), from.x, from.y, from.z, to.yaw, to.pitch))
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageEvent) {
        if (event.entity is Player) {
            drinkingOrder(event.entity as Player)
        }
    }

    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent) {
        if (event.itemDrop.itemStack == continuingItem) {
            event.player.sendMessage("Vollen Alkohol wirft man nicht weg!")
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerSwapHands(event: PlayerSwapHandItemsEvent) {
        if (event.offHandItem == continuingItem) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem == continuingItem) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityMount(event: EntityMountEvent) {
        if (event.mount.type == EntityType.HORSE || event.mount.type == EntityType.MULE || event.mount.type == EntityType.DONKEY) {
            val player = event.entity as Player
            event.isCancelled = true
            player.sendMessage("${ChatColor.RED}Du darfst nicht während du trinkst Pferde reiten")
            drinkingOrder(player)
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (forbiddenBlocks.contains(event.block.type)) {
            drinkingOrder(event.player)
        }
    }

    @EventHandler
    fun onItemUse(event: PlayerItemConsumeEvent) {
        if (event.item.type == continuingItem.type) {
            event.isCancelled = true
            if (mustDrink) {
                if (event.player == drinker) {
                    continueGame(event.player)
                }
            }
        }
    }

    /*@EventHandler
    fun onItemEntityUse(event: PlayerInteractEntityEvent){
        if(event.item?.type == continuingItem.type){
            if(mustDrink) {
                if (event.player == drinker) {
                    continueGame(event.player)
                }
            }
        }
    }*/

    override fun updateAndGetCharacterizedItem(): ItemStack =
        createGuiItem(Material.HONEY_BOTTLE, "§a$name", "§7$description", "§7Status: " + if (isEnabled) "§aAn" else "§cAus")

    private fun continueGame(player: Player) {
        mustDrink = false
        drinker = null

        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle("${ChatColor.GREEN}Es geht weiter", "", 1, 30, 1)
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        }
    }

    private fun drinkingOrder(player: Player) {
        if (mustDrink) {
            return
        }

        drinker = player
        mustDrink = true

        player.sendTitle(
            "${ChatColor.DARK_RED}${ChatColor.BOLD} TRINK",
            "${ChatColor.GRAY} Wenn du fertig bist rechtsklick die Honigflasche",
            1,
            30,
            1,
        )
        val players = Bukkit.getOnlinePlayers().toMutableList()
        players.forEach { p -> p.playSound(player.location, Sound.BLOCK_BELL_USE, 10f, 0.1f) }
        players.remove(player)
        players.forEach { p ->
            p.sendTitle("${ChatColor.RED}${player.name} muss trinken!", "${ChatColor.GRAY} Du musst auf ihn warten...", 1, 30, 1)
        }
    }
}
