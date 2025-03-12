package yt.lost.kChallengesNew.base.menus

import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.settings.Settings

object GamePreparation {

    val activeChallenges: MutableList<Challenge> = ArrayList()
    lateinit var settings: Settings
}