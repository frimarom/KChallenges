package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.util.Vector
import yt.lost.kChallengesNew.base.challenges.Challenge

class MoreKnockbackChallenge : Challenge() {
    init {
        material = Material.FEATHER
        name = "More Knockback"
        description = listOf("Jeder Spieler und Mob hat 20 mal mehr Knockback")
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.entity.velocity = Vector(0.0, event.finalDamage * 3.0, 0.0)
        }
    }

    @EventHandler
    fun onEntityByEntityDamage(event: EntityDamageByEntityEvent) {
        val p: Entity = event.entity

        p.velocity =
            event.damager
                .location
                .direction
                .setY(0)
                .normalize()
                .multiply(20)
    }

    override fun onStart() {
    }
}
