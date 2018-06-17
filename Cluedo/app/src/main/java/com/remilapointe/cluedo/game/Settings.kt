package com.remilapointe.cluedo.game

import com.remilapointe.cluedo.game.cards.Person

const val TAG: String = "CLUEDO"
const val MAX_PLAYERS = 6

const val REQ_CODE_INPUT_NB_PLAYER = 1
const val REQ_CODE_HELP_PLAYERS = 2
const val REQ_CODE_SELECTED_CARDS = 3

//const val EXTRA_NB_PLAYERS = "nb_players"
//const val EXTRA_GAME_MODE = "game_mode"
const val GAME_MODE_ALONE = 1
const val GAME_MODE_HELP = 2
//const val EXTRA_PLAYER_ID = "player_id"

class Settings {

    companion object {
        var gameMode = 0
        var nbPlayers = 0

        var playerName: Array<String> = Array(MAX_PLAYERS) {_ -> ""}
        var playerPerson: Array<Person> = Array(MAX_PLAYERS) {_ -> Person("a")}

        var yourPlayerNumber = 0
        var game: GameActions = GameActions()
    }

}