package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.utils.Backpack

class Team(
    val color: ChatColor,
    val name: String,
    val invLocation: Int,
    material: Material,
) {
    val member: MutableList<Player> = mutableListOf()

    val backpack: Backpack = Backpack(this)
    private val characterizedItem = createGuiItem(material, "$color" + name, *member.map { "$color" + it.name }.toTypedArray())

    fun updateAndGetCharacterizedItem(): ItemStack {
        val meta = characterizedItem.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = member.map { "$color" + it.name }
        characterizedItem.itemMeta = meta
        return characterizedItem
    }

    fun sendMessageToMember(message: String) {
        member.forEach { player -> player.sendMessage(message) }
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
}
