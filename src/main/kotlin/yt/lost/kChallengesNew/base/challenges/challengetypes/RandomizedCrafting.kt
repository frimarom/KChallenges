package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Bukkit
import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.challenges.Challenge

class RandomizedCrafting : Challenge() {
    private var blocksDrops: MutableMap<NamespacedKey, Material> = HashMap()

    init {
        material = Material.CRAFTER
        name = "Randomized Crafting"
        description = listOf("Beim Craften kommen andere Sachen raus als man eigentlich erwartet")
    }

    override fun onStart() {
        val iterator = Bukkit.recipeIterator()

        while (iterator.hasNext()) {
            val recipe = iterator.next()

            if (recipe is Keyed) {
                val key = (recipe as Keyed).key
                val result: Material =
                    Material.entries
                        .filter { it.isItem && it.isBlock }
                        .shuffled()
                        .firstOrNull { it != recipe.result.type && it != Material.AIR } as Material
                blocksDrops.put(key, result)
            }
        }
    }

    @EventHandler
    fun onCraft(event: PrepareItemCraftEvent) {
        val recipe = event.recipe

        if (recipe == null) return
        if (recipe !is Keyed) return

        val key = (recipe as Keyed).key
        val override: Material? = blocksDrops[key]

        if (override != null) {
            val newResult = ItemStack(override, 1)
            event.inventory.result = newResult
        }
    }
}
