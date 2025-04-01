package yt.lost.kChallengesNew.utils

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import yt.lost.kChallengesNew.base.teamgamemode.Team

class Backpack(
    val team: Team?,
) {
    var backpack: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Backpack")
}
