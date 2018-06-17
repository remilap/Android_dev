package com.remilapointe.cluedo.game.cards

import android.util.Log
import com.remilapointe.cluedo.game.TAG

class Weapon(name: String) : Card(name) {

    init {
        Log.i(TAG,"Init Weapon with name=$name")
    }

}
