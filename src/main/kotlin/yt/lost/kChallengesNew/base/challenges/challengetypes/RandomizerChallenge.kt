package yt.lost.kChallengesNew.base.challenges.challengetypes

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import yt.lost.kChallengesNew.base.challenges.Challenge
import java.util.*
import kotlin.collections.HashMap

class RandomizerChallenge() : Challenge() {

    override val name: String = "Block Randomizing Challenge"
    override val description: String = "Die Drops von Blöcken werden zufällig vertauscht"

    private var blocksDrops: MutableMap<Material, List<Material>> = mutableMapOf()

    override fun onStart() {
        val allBlocks = Material.values().filter { it.isBlock }.shuffled()
        val allItems = Material.values().filter { it.isItem }.shuffled()

        val dropQueue = ArrayDeque(allItems)
        var blockQueue = ArrayDeque(allBlocks)

        while(dropQueue.isNotEmpty()) {
            if(blockQueue.isEmpty())
                blockQueue = ArrayDeque(allBlocks)

            val block = blockQueue.removeFirst()
            var assignedDrops = mutableListOf<Material>()

            if(blocksDrops.containsKey(block))
                assignedDrops = blocksDrops[block]?.toMutableList()!!

            assignedDrops.add(dropQueue.removeFirst() ?: return)

            blocksDrops[block] = assignedDrops
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val blockType = event.block.type
        val newDrop = blocksDrops[blockType] ?: return

        // Verhindert Standard-Drops
        if(event.isDropItems) {
            event.isDropItems = false
            for (drop in newDrop) {
                val dropLocation = event.block.location
                dropLocation.x += 0.5
                dropLocation.y += 0.5
                dropLocation.z += 0.5
                event.block.world.dropItemNaturally(event.block.location, ItemStack(drop))
            }
        }
    }

    override fun updateAndGetCharacterizedItem(): ItemStack {
        return createGuiItem(Material.CHEST, "§a$name", "§7$description", "§7Status: " + if (isEnabled) "§aAn" else "§cAus")
    }
}