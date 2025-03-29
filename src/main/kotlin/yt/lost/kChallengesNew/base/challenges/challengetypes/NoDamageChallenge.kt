package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.base.challenges.Challenge

class NoDamageChallenge: Challenge() {

    override val name: String = "No Damage"
    override val description: String = "Die Spieler dürfen keinen Schaden bekommen"

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun updateAndGetCharacterizedItem(): ItemStack {
        return createGuiItem(
            Material.REDSTONE_BLOCK,
            "§a$name",
            "§7$description",
            "§7Status: " + if (isEnabled) "§aAn" else "§cAus"
        )
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent){
        if(event.entity is Player)
            game?.stop("Der Spieler ${(event.entity as Player).name} hat Schaden genommen")
    }
}