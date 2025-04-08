package yt.lost.kChallengesNew.settings

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.ItemFlag
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
    var scoreboard: Boolean = false,
) {
    val settingsList: MutableList<Setting> = mutableListOf()

    init {
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.GOLDEN_APPLE,
                    "§6Ultra Hardcore",
                    "",
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
                    "",
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
                    "",
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
                    "",
                    "§8Sterben ist ${if (canDie){
                        "§aErlaubt"
                    }else {
                        "§cnicht Erlaubt"
                    }}",
                    "${ChatColor.DARK_GRAY}Wenn ${ChatColor.RED}Nicht Erlaubt ${ChatColor.DARK_GRAY}ist, ist die Challenge",
                    "${ChatColor.DARK_GRAY}nach dem Sterben eines Spielers vorbei",
                )
            }, {
                canDie = !canDie
            }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.WHITE_BED,
                    "${ChatColor.GOLD}Anzahl Teams",
                    "",
                    "${ChatColor.RED}Nur bei Team Game Modes verfügbar!",
                    "${ChatColor.DARK_GRAY}Wie viele Teams gibt es?",
                    "${ChatColor.DARK_GRAY}Anzahl: ${ChatColor.GREEN}$teamAmount",
                    "${ChatColor.DARK_GRAY}Linksklick ${ChatColor.GOLD}+1 Team",
                    "${ChatColor.DARK_GRAY}Rechtsklick ${ChatColor.GOLD}-1 Team",
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
                    "${ChatColor.GOLD}Timer Countdown",
                    " ",
                    "${ChatColor.RED}Nur bei Team Game Modes verfügbar!",
                    "${ChatColor.DARK_GRAY}Wie lange soll das Spiel gehen?",
                    "${ChatColor.DARK_GRAY}Zeit: ${ChatColor.GREEN}${formatTime(timerCountdown)}",
                    "${ChatColor.DARK_GRAY}Linksklick ${ChatColor.GOLD}+10 min",
                    "${ChatColor.DARK_GRAY}Rechtsklick ${ChatColor.GOLD}-10 min",
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
                    "${ChatColor.GOLD}Spectator",
                    "",
                    "${ChatColor.DARK_GRAY}Dürfen andere Spieler zuschauen?",
                    "${ChatColor.DARK_GRAY}Spectator sind: ${if (spectator){
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
                    "${ChatColor.GOLD}Manuell Ergebnis anzeigen",
                    "",
                    "${ChatColor.DARK_GRAY}Wenn ${ChatColor.GREEN}An ${ChatColor.DARK_GRAY}ist dann werden die Ergebnisse ",
                    "${ChatColor.DARK_GRAY}erst angezeigt nachdem man ${ChatColor.GREEN}/reveal${ChatColor.DARK_GRAY} macht",
                    "${ChatColor.DARK_GRAY}Manuelle-Ergebnis-Anzeige ist: ${if (manualResultReveal){
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
                    "${ChatColor.GOLD}PVP",
                    "",
                    "",
                )
            }, { pvp = !pvp }, {}),
        )
        settingsList.add(
            Setting({
                createGuiItem(
                    Material.BOOK,
                    "${ChatColor.GOLD}Scoreboard",
                    "",
                    "${ChatColor.DARK_GRAY}Soll man einen rechts einen Zwischenstand",
                    "${ChatColor.DARK_GRAY}der Ergebnisse sehen?",
                    "${ChatColor.DARK_GRAY}Scoreboard ist: ${if (scoreboard){
                        "${ChatColor.GREEN}An"
                    }else {
                        "${ChatColor.RED}Aus"
                    }
                    }",
                )
            }, { scoreboard = !scoreboard }, {}),
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

        meta?.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_ENCHANTS,
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_DESTROYS,
            ItemFlag.HIDE_PLACED_ON,
            ItemFlag.HIDE_DYE,
        )

        meta?.setDisplayName(name)
        meta?.lore = lore.toMutableList()
        item.itemMeta = meta

        return item
    }

    /*fun removeBundleItems(bundle: ItemStack?): ItemStack? {
        if (bundle == null || bundle.type != Material.BUNDLE) return bundle

        // Entferne alle Bundle-Inhalte aus dem NBT
        val nmsStack: ItemStack = CraftItemStack.asNMSCopy(bundle)
        val tag: net.minecraft.nbt.NBTTagCompound = nmsStack.u() // = getOrCreateTag()

        if (tag != null) {
            tag.r("Items") // Entfernt den "Items"-NBT-Tag
        }

        return CraftItemStack.asBukkitCopy(nmsStack)
    }*/
}
