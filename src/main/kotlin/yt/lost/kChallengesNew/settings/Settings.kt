package yt.lost.kChallengesNew.settings

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.ItemStack

class Settings(
    var isChallenge: Boolean = false,
    var uhc: Boolean = false,
    var canDie: Boolean = false,
    var pvp: Boolean = true,
    var spectator: Boolean = true,
    var backpack: Boolean = false,
    var teamAmount: Int = 1,
    var timerCountdown: Int = 3600,
    var manualResultReveal: Boolean = false,
) {
    val settingsList: MutableList<Setting> = mutableListOf()

    init {
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.GOLDEN_APPLE,
                    "§6Ultra Hardcore",
                    "§8Ultrahardcore ist ${if (uhc){
                        "§aAn"
                    }else {
                        "§cAus"
                    }}",
                )
            }, { uhc = !uhc }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.REDSTONE,
                    "§cMaximale Leben ",
                    "§8Maximale Leben: §7${
                        Bukkit
                            .getOnlinePlayers()
                            .random()
                            .getAttribute(Attribute.MAX_HEALTH)
                            ?.baseValue!! / 2
                    }§c♥",
                    "§8Linksklick §8zum §aerhöhen",
                    "§8Rechtsklick §8zum §averringern",
                )
            }, {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.getAttribute(Attribute.MAX_HEALTH)?.baseValue = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!! + 1.0
                    player.health = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!!
                }
            }, {
                for (player in Bukkit.getOnlinePlayers()) {
                    if ((player.getAttribute(Attribute.MAX_HEALTH)?.value)!! - 1.0 < 1.0) {
                        it.sendMessage("${ChatColor.RED}Die Maximalen Leben dürfen nicht kleiner als 0.5♥ sein")
                        return@Setting
                    }
                    player.getAttribute(Attribute.MAX_HEALTH)?.baseValue =
                        (player.getAttribute(Attribute.MAX_HEALTH)?.value)!! - 1.0
                    player.health = (player.getAttribute(Attribute.MAX_HEALTH)?.value)!!
                }
            }),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.BUNDLE,
                    "${ChatColor.GREEN}Backpack",
                    "${ChatColor.DARK_GRAY}Backpack ist ${if (backpack){
                        "${ChatColor.GREEN}An"
                    }else {
                        "${ChatColor.RED}Aus"
                    }}",
                )
            }, {
                backpack = !backpack
            }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.PLAYER_HEAD,
                    "§6Sterben",
                    "§8Sterben ist ${if (canDie){
                        "§aErlaubt"
                    }else {
                        "§cnicht Erlaubt"
                    }}",
                    "§8Wenn §cNicht Erlaubt §8ist, ist die Challenge nach dem Sterben eines Spielers vorbei",
                )
            }, {
                canDie = !canDie
            }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.WHITE_BED,
                    "Anzahl Teams",
                    "Nur bei Team Game Modes verfügbar!",
                    "Anzahl: $teamAmount",
                    "Linksklick +1 Team",
                    "Rechtsklick -1 Team",
                )
            }, {
                if (isChallenge) {
                    it.sendMessage("${ChatColor.RED}Du kannst während einer Challenge nicht die Teamanzahl verändern")
                    return@Setting
                }
                if (teamAmount + 1 >= Bukkit.getOnlinePlayers().size) {
                    it.sendMessage("${ChatColor.RED}Die Teamanzahl darf die Spieleranzahl nicht überschreiten")
                    return@Setting
                }
                teamAmount += 1
            }, {
                if (isChallenge) {
                    it.sendMessage("${ChatColor.RED}Du kannst während einer Challenge nicht die Teamanzahl verändern")
                    return@Setting
                }
                if (teamAmount - 1 < 1) {
                    it.sendMessage("${ChatColor.RED}Die Teamanzahl darf nicht kleiner als 1 sein")
                    return@Setting
                }
                teamAmount -= 1
            }),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.CLOCK,
                    "Timer Countdown",
                    "Nur bei Team Game Modes verfügbar!",
                    "Zeit: ${formatTime(timerCountdown)}",
                    "Linksklick +10 min",
                    "Rechtsklick -10 min",
                )
            }, {
                if (isChallenge) {
                    it.sendMessage("${ChatColor.RED}Du kannst während einer Challenge nicht die Zeit verändern")
                    return@Setting
                }
                timerCountdown += 600
            }, {
                if (isChallenge) {
                    it.sendMessage("${ChatColor.RED}Du kannst während einer Challenge nicht die Zeit verändern")
                    return@Setting
                }
                if (timerCountdown - 600 < 600) {
                    it.sendMessage("${ChatColor.RED}Der Timer darf nicht kleiner als 10 min sein")
                    return@Setting
                }
                timerCountdown -= 600
            }),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.SKELETON_SKULL,
                    "Spectator",
                    "Spectator sind: ${if (spectator){
                        "${ChatColor.GREEN}An"
                    }else {
                        "${ChatColor.RED}Aus"
                    }
                    }",
                )
            }, {
                spectator = !spectator
            }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.FILLED_MAP,
                    "Manueller Ergebnis anzeigen",
                    "Wenn ",
                    "Manuelle Ergebnis Anzeige ist: ${if (manualResultReveal){
                        "${ChatColor.GREEN}An"
                    }else {
                        "${ChatColor.RED}Aus"
                    }
                    }",
                )
            }, {
                manualResultReveal = !manualResultReveal
            }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.NETHERITE_SWORD,
                    "PVP",
                    "",
                )
            }, { pvp = !pvp }, {}),
        )
    }

    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
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
