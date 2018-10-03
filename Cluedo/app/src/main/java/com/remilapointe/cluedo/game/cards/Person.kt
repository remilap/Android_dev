package com.remilapointe.cluedo.game.cards

import com.remilapointe.cluedo.Util

class Person(name: String) : Card(name) {

    init {
        Util.log("Init Person with name=$name")
    }

}
