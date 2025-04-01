package yt.lost.kChallengesNew.base

import yt.lost.kChallengesNew.settings.Settings
import yt.lost.kChallengesNew.utils.Timer

abstract class Game {
    abstract val isRunning: Boolean
    abstract val timer: Timer

    var settings: Settings = Settings()

    abstract fun start()

    abstract fun stop(cause: String)
}
