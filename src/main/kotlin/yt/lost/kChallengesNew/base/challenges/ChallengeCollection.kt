package yt.lost.kChallengesNew.base.challenges

import yt.lost.kChallengesNew.base.challenges.challengetypes.*

class ChallengeCollection {
    val challenges: MutableList<Challenge> = ArrayList()

    init {
        challenges.add(NoDamageChallenge())
        challenges.add(NoCraftingTable())
        challenges.add(MoreKnockbackChallenge())
        challenges.add(AlkoholChallenge())
        challenges.add(RandomizerChallenge())
        challenges.add(RandomizedCrafting())
    }
}
