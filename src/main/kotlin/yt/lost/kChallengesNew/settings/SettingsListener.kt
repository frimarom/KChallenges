package yt.lost.kChallengesNew.settings

import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.PlayerDeathEvent
import yt.lost.kChallengesNew.base.Game

class SettingsListener(private val game: Game, private val settings: Settings): Listener {

    @EventHandler
    fun onHealthRegenerate(event: EntityRegainHealthEvent){
        if(settings.uhc && event.regainReason == EntityRegainHealthEvent.RegainReason.REGEN && event.regainReason == EntityRegainHealthEvent.RegainReason.EATING)
            event.isCancelled = true
    }

    @EventHandler
    fun onPlayerDie(event: PlayerDeathEvent){
        if(!settings.canDie)
            game.stop("Der Spieler ${event.entity.name} ist gestorben")
    }

    @EventHandler
    fun onEnderDragonDeath(event: EntityDeathEvent){
        if(event.entity.type == EntityType.ENDER_DRAGON ) {
            Bukkit.getOnlinePlayers().forEach { player -> player.sendTitle("Geschafft!", "Geilo") }
            game.stop("Ihr habt die Challenge geschafft!")
        }
    }
}
