package yt.lost.kChallengesNew.base.challenges

import yt.lost.kChallengesNew.base.Game
import yt.lost.kChallengesNew.base.challenges.challengetypes.AlkoholChallenge
import yt.lost.kChallengesNew.base.challenges.challengetypes.MoreKnockbackChallenge
import yt.lost.kChallengesNew.base.challenges.challengetypes.NoCraftingTable
import yt.lost.kChallengesNew.base.challenges.challengetypes.NoDamageChallenge

class ChallengeCollection(game: Game) {
    val challenges: MutableList<Challenge> = ArrayList()

    init {
        challenges.add(NoDamageChallenge(game))
        challenges.add(NoCraftingTable(game))
        challenges.add(MoreKnockbackChallenge(game))
        challenges.add(AlkoholChallenge(game))
    }
}