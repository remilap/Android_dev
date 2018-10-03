package com.remilapointe.cluedo.game.cards

import com.remilapointe.cluedo.Util

class Place(name: String) : Card(name) {

    init {
        Util.log("Init Place with name=$name")
    }

}
