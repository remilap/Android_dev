package com.remilapointe.cluedo.game

import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_UNKNOWN

const val TAG: String = "CLUEDO"
const val MAX_PLAYERS = 6

const val REQ_CODE_INPUT_NB_PLAYER = 1
const val REQ_CODE_HELP_PLAYERS = 2
const val REQ_CODE_SELECTED_CARDS = 3

class Settings {

    companion object {
        var nbPlayers = 0
        var gameMode: Int = GAME_MODE_UNKNOWN

        //        var playerName: Array<String> = Array(MAX_PLAYERS) {_ -> ""}
//        var playerPerson: Array<Person> = Array(MAX_PLAYERS) {_ -> Person("a")}
        var players: MutableList<Player> = mutableListOf()

        var yourPlayerNumber = 0
        var game: GameActions = GameActions()
        var playerTurn = 0
    }

}