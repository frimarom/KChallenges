package yt.lost.kChallengesNew.utils

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

class Backpack {

    var backpack: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Backpack")
}