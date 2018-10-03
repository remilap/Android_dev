package com.remilapointe.cluedo.game

interface GameItf {
    companion object {
        const val GAME_MODE_UNKNOWN = 0
        const val GAME_MODE_ALONE = 1
        const val GAME_MODE_HELP = 2
    }

    fun setGameMode(mode: Int)
    fun getGameMode(): Int
}