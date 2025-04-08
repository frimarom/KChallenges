package yt.lost.kChallengesNew.settings

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.PlayerDeathEvent
import yt.lost.kChallengesNew.base.Game

class SettingsListener(
    private val game: Game,
    private val settings: Settings,
) : Listener {
    @EventHandler
    fun onHealthRegenerate(event: EntityRegainHealthEvent) {
        if (settings.uhc &&
            (
                event.regainReason == EntityRegainHealthEvent.RegainReason.REGEN ||
                    event.regainReason == EntityRegainHealthEvent.RegainReason.EATING
            )
        ) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerDie(event: PlayerDeathEvent) {
        if (!settings.canDie) {
            if (settings.isChallenge) {
                game.stop("Der Spieler ${event.entity.name} ist gestorben")
            } else {
                // TODO
                event.entity.sendMessage("Du wärst jetzt ein Spectator")
            }
        }
    }

    @EventHandler
    fun onEnderDragonDeath(event: EntityDeathEvent) {
        if (!settings.isChallenge) {
            return
        }
        if (event.entity.type == EntityType.ENDER_DRAGON) {
            Bukkit.getOnlinePlayers().forEach { player -> player.sendTitle("Geschafft!", "Geilo") }
            game.stop("Ihr habt die Challenge geschafft!")
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player) {
            return
        }
        val damager = event.damager as Player
        if (!settings.pvp) {
            event.isCancelled = true
            damager.sendMessage("${ChatColor.RED}PVP ist aus")
            return
        }
        if (!settings.isChallenge) {
            if (game.timer.currentTime <= 600) {
                event.isCancelled = true
                damager.sendMessage("${ChatColor.RED}In den ersten ${ChatColor.DARK_GRAY}10 min${ChatColor.RED} ist PVP aus")
            }
        }
    }
}
