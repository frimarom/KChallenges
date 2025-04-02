package yt.lost.kChallengesNew.base.challenges

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.Game

abstract class Challenge : Listener {
    var isEnabled: Boolean = false
    var game: Game? = null

    var material: Material = Material.AIR
    var name: String = ""
    var description: List<String> = listOf()

    fun updateAndGetCharacterizedItem(): ItemStack =
        createGuiItem(
            material,
            "${ChatColor.GREEN}$name",
            *description.map { "${ChatColor.GRAY}$it" }.toTypedArray(),
            "${ChatColor.GRAY}Status: " + if (isEnabled) "§aAn" else "§cAus",
        )

    abstract fun onStart()

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
