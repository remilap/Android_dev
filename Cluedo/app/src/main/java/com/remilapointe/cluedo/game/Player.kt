package com.remilapointe.cluedo.game

import android.util.Log
import com.remilapointe.cluedo.game.cards.Card

class Player(val name: String, cPerson: Card) {
    var cardsInHand: MutableList<Card> = mutableListOf()
    var shownCards: MutableList<Card> = mutableListOf()
    var idx: Int = -1

    companion object {
        @JvmStatic
        var nextId: Int = 0
    }

    init {
        idx = Player.nextId++
        Log.i(TAG,"Init Player with name=$name and idx=$idx and cPerson=${cPerson.name}")
    }
}
