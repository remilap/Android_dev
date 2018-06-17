package com.remilapointe.cluedo.game.cards

import android.util.Log
import com.remilapointe.cluedo.game.TAG

const val CARD_IN_UNKNOWN = 1
const val CARD_IN_SOLUCE = 2
const val CARD_IN_PLAYER_HAND = 3

abstract class Card(val name: String) {
    var where = CARD_IN_UNKNOWN
    var idx: Int = -1

    companion object {
        @JvmStatic var nextId: Int = 0
    }

    init {
        idx = nextId++
        Log.i(TAG,"Init Card with name=$name and idx=$idx")
    }

}
