package yt.lost.kChallengesNew.base

import yt.lost.kChallengesNew.base.challenges.Challenge
import yt.lost.kChallengesNew.base.teamgamemode.TeamGameMode
import yt.lost.kChallengesNew.settings.Settings

class GamePreparation(var isChallenge: Boolean = false,
                      var settings: Settings,
                      var challenges: List<Challenge> = listOf(),
                      var teamGameMode: TeamGameMode?)  {


}