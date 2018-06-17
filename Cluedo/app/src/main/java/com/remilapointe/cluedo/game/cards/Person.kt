package com.remilapointe.cluedo.game.cards

import android.util.Log
import com.remilapointe.cluedo.game.TAG

class Person(name: String) : Card(name) {
    var x: Int = -1
    var y: Int = -1

    init {
        Log.i(TAG,"Init Person with name=$name")
    }

}
