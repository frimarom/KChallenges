package yt.lost.kChallengesNew.base.menus

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.GamePreparation

abstract class SelectionMenu(
    protected val gamePreparation: GamePreparation,
) : Listener {
    abstract val inventory: Inventory

    protected fun createGuiItem(
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
}
