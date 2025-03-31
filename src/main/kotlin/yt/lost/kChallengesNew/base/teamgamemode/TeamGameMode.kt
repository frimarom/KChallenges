package yt.lost.kChallengesNew.base.teamgamemode

import org.bukkit.inventory.ItemStack

abstract class TeamGameMode {

    val participatingTeams: MutableList<Team> = mutableListOf()

    abstract fun start()
    abstract fun stop()
    abstract fun updateAndGetCharacterizedItem(): ItemStack
}
