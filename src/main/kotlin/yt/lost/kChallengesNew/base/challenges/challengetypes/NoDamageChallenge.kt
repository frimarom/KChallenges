package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import yt.lost.kChallengesNew.base.challenges.Challenge

class NoDamageChallenge : Challenge() {
    init {
        material = Material.REDSTONE_BLOCK
        name = "No Damage"
        description = listOf("Die Spieler dürfen keinen Schaden bekommen")
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity is Player) {
            game?.stop("Der Spieler ${(event.entity as Player).name} hat Schaden genommen")
        }
    }
}
