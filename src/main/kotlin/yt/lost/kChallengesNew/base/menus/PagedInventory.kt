package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class PagedInventory(
    private val player: Player,
    private val title: String,
    private val items: List<ItemStack>,
    private val size: Int = 54,
) : Listener {
    private var currentPage = 0
    private val itemsPerPage = size - 9 // z.B. untere Reihe für Navigation
    private val inventories: MutableMap<Int, Inventory> = mutableMapOf()

    // TODO Sortieren nach Items machen
    fun openPage(page: Int) {
        if (inventories[page] != null) {
            currentPage = page
            player.openInventory(inventories[page]!!)
            return
        }
        val inv = Bukkit.createInventory(null, size, "$title - Seite ${page + 1}")
        val startIndex = page * itemsPerPage
        val pageItems = items.drop(startIndex).take(itemsPerPage)

        for ((index, item) in pageItems.withIndex()) {
            inv.setItem(index, item)
        }

        // Navigation
        if (page > 0) {
            inv.setItem(size - 9, createGuiItem(Material.RED_STAINED_GLASS_PANE, "${ChatColor.GOLD}← Zurück"))
        }
        if ((page + 1) * itemsPerPage < items.size) {
            inv.setItem(size - 1, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "${ChatColor.GOLD}Weiter →"))
        }

        currentPage = page
        inventories[page] = inv
        player.openInventory(inv)
    }

    @EventHandler
    fun handleClick(event: InventoryClickEvent) {
        if (!inventories.values.contains(event.inventory)) {
            return
        }

        event.isCancelled = true

        when (event.slot) {
            size - 9 -> if (currentPage > 0) openPage(currentPage - 1)
            size - 1 -> if ((currentPage + 1) * itemsPerPage < items.size) openPage(currentPage + 1)
            else -> {}
        }
    }

    fun open() {
        openPage(0)
    }

    private fun createGuiItem(
        material: Material,
        name: String,
        vararg lore: String?,
    ): ItemStack {
        val item = ItemStack(material, 1)
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = lore.toMutableList()
        item.itemMeta = meta

        return item
    }

    /*private fun createNavItem(name: String): ItemStack =
        ItemStack(Material.ARROW).apply {
            itemMeta =
                itemMeta?.also {
                    it.setDisplayName(name)
                }
        }*/
}
