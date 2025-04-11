package yt.lost.kChallengesNew.base

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.entity.PlayerMock
import yt.lost.kChallengesNew.KChallengesNew

class ProgressiveGameCreatorTest {
    private var server: ServerMock? = null
    private var plugin: KChallengesNew? = null
    private var testPlayer: PlayerMock? = null

    private var classToTest: ProgressiveGameCreator? = null

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(KChallengesNew::class.java)
        testPlayer = server?.addPlayer()

        classToTest = ProgressiveGameCreator(testPlayer!!, plugin!!)
    }

    @Test
    fun testNormalChallengeProgression() {
        testPlayer!!.performCommand("challenge")
    }
}
