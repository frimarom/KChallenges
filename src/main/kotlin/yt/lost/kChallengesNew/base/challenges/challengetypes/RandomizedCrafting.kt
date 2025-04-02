package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import yt.lost.kChallengesNew.base.challenges.Challenge

class RandomizedCrafting : Challenge() {
    init {
        material = Material.CRAFTER
        name = "Randomized Crafting"
        description = listOf("Beim Craften kommen andere Sachen raus als man eigentlich erwartet")
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }
}
