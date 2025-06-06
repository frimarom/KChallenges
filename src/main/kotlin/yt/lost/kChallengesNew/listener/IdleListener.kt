package yt.lost.kChallengesNew.listener

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.ProgressiveGameCreator

class IdleListener(
    private val progressiveGameCreator: ProgressiveGameCreator,
) : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.joinMessage = "§7>>§kh§r§a${event.player.name}§r§kh"

        event.player.setPlayerListHeaderFooter("", "")

        if (!progressiveGameCreator.isCreating) {
            return
        }

        event.player.inventory.clear()
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        event.quitMessage = "§7<<§kh§r§4${event.player.name}§kh"
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (progressiveGameCreator.isCreating) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (progressiveGameCreator.isCreating) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityTarget(event: EntityTargetEvent) {
        if (progressiveGameCreator.isCreating && event.target is Player) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageEvent) {
        if (progressiveGameCreator.isCreating) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!progressiveGameCreator.isCreating) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent) {
        if (!progressiveGameCreator.isCreating) {
            return
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (!progressiveGameCreator.isCreating) {
            return
        }
        if (event.inventory != event.whoClicked.inventory) {
            return
        }

        event.isCancelled = true

        val item: ItemStack? = event.currentItem

        when (item?.type) {
            Material.ENDER_PEARL -> {
                (event.whoClicked as Player).performCommand("challenges")
            }
            Material.BELL -> {
                (event.whoClicked as Player).performCommand("settings")
            }
            Material.OAK_BOAT -> {
                (event.whoClicked as Player).performCommand("teams")
            }
            else -> {}
        }
    }
}
