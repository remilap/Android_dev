package com.remilapointe.cluedo.game.cards

import android.util.Log
import com.remilapointe.cluedo.game.TAG

class Place(name: String) : Card(name) {

    init {
        Log.i(TAG,"Init Place with name=$name")
    }

}
