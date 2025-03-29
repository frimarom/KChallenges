package yt.lost.kChallengesNew.base.challenges

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.Game

abstract class Challenge : Listener {

    var isEnabled: Boolean = false
    var game: Game? = null

    abstract val name: String
    abstract val description: String
    abstract var characterizedItem: ItemStack

    abstract fun onStart()

    protected fun createGuiItem(material: Material, name: String, vararg lore: String?): ItemStack {
        val item = ItemStack(material, 1)
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = lore.toMutableList()
        item.itemMeta = meta

        return item
    }
}