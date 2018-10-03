package com.remilapointe.cluedo.game.cards

import com.remilapointe.cluedo.Util

class Weapon(name: String) : Card(name) {

    init {
        Util.log("Init Weapon with name=$name")
    }

}
