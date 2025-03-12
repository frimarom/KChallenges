package yt.lost.kChallengesNew.base.menus

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.settings.Settings

class SettingsSelectionMenu(private val game: Game): Listener {
    var settingsInventory: Inventory = Bukkit.createInventory(null, InventoryType.BARREL, "Einstellungen")

    init {
        this.settingsInventory.setItem(10,
            createGuiItem(
                Material.REDSTONE,
                "§cMaximale Leben ",
                "§8Maximale Leben: §7${20.0}§c♥",
                "§8Linksklick §8zum §aerhöhen",
                "§8Rechtsklick §8zum §averringern")
        )
        this.settingsInventory.setItem(11,
            createGuiItem(
                Material.GOLDEN_APPLE,
                "§6Ultra Hardcore",
                "§8Ultrahardcore ist ${if(Settings.uhc){"§aAn"}else{"§cAus"}}")
        )
        this.settingsInventory.setItem(13,
            createGuiItem(
                Material.PLAYER_HEAD,
                "§6Sterben",
                "§8Sterben ist ${if(Settings.canDie){"§aErlaubt"}else{"§cnicht Erlaubt"}}",
                "§8Wenn §cNicht Erlaubt §8ist, ist die Challenge nach dem Sterben eines Spielers vorbei")
        )
        this.settingsInventory.setItem(26,
            createGuiItem(
                Material.GREEN_WOOL,
                "Starten"
            )
        )
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent){
        if(event.inventory != this.settingsInventory)
            return


        event.isCancelled = true

        val item = event.currentItem
        val clicker = event.whoClicked as Player

        if(event.isLeftClick){
            when (item?.type) {
                Material.REDSTONE -> {
                    for(player in Bukkit.getOnlinePlayers()){
                        player.getAttribute(Attribute.MAX_HEALTH)?.baseValue = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!! + 1.0
                        player.health = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!!
                    }
                    this.settingsInventory.setItem(
                        10,
                        createGuiItem(
                            Material.REDSTONE,
                            "§cMaximale Leben ",
                            "§8Maximale Leben: §7${
                                Bukkit.getOnlinePlayers().random().getAttribute(Attribute.MAX_HEALTH)?.baseValue
                            }§c♥",
                            "§8Linksklick §8zum §aerhöhen",
                            "§8Rechtsklick §8zum §averringern"))
                }
                Material.GOLDEN_APPLE -> {
                    Settings.uhc = !Settings.uhc
                    this.settingsInventory.setItem(11,
                        createGuiItem(
                            Material.GOLDEN_APPLE,
                            "§6Ultra Hardcore",
                            "§7Ultrahardcore ist ${if(Settings.uhc){"§aAn"}else{"§cAus"}}"))

                }
                Material.PLAYER_HEAD -> {
                    Settings.canDie = !Settings.canDie
                    this.settingsInventory.setItem(13,
                        createGuiItem(
                            Material.PLAYER_HEAD,
                            "§6Sterben",
                            "§8Sterben ist ${if(Settings.canDie){"§aErlaubt"}else{"§cnicht Erlaubt"}}",
                            "§8Wenn §cNicht Erlaubt §8ist, ist die Challenge nach dem Sterben eines Spielers vorbei"))

                }
                Material.GREEN_WOOL -> {
                    clicker.closeInventory()
                    game.start(Settings)
                }
                else -> {}
            }
        }else if (event.isRightClick){
            if(item?.type == Material.REDSTONE) {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.getAttribute(Attribute.MAX_HEALTH)?.baseValue =
                        (player.getAttribute(Attribute.MAX_HEALTH)?.value)!! - 1.0
                    player.health = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!!
                }
                this.settingsInventory.setItem(
                    10,
                    createGuiItem(
                        Material.REDSTONE,
                        "§cMaximale Leben ",
                        "§8Maximale Leben: §7${
                            Bukkit.getOnlinePlayers().random().getAttribute(Attribute.MAX_HEALTH)?.baseValue
                        }§c♥",
                        "§8Linksklick §8zum §aerhöhen",
                        "§8Rechtsklick §8zum §averringern"
                    )
                )
            }
        }
    }

    @EventHandler
    fun inventoryCloseEvent(event: InventoryCloseEvent){
        if(event.inventory == this.settingsInventory){
            HandlerList.unregisterAll(this)
        }
    }

    private fun createGuiItem(material: Material, name: String, vararg lore: String?): ItemStack {
        val item = ItemStack(material, 1)
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = lore.toMutableList()
        item.itemMeta = meta

        return item
    }
}