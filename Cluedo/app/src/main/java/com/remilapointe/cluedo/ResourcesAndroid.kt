package com.remilapointe.cluedo

import android.content.Context
import android.util.Log
import com.remilapointe.cluedo.game.ResourcesItf
import com.remilapointe.cluedo.game.ResourcesAbstract
import com.remilapointe.cluedo.game.TAG

class ResourcesAndroid(var ctx: Context): ResourcesItf, ResourcesAbstract() {
    init {
        Log.i(TAG, this.javaClass.simpleName + ".init")
    }

    override fun getPlacesNames(): Array<String> {
        Log.i(TAG, this.javaClass.simpleName + ".getPlacesNames")
        return ctx.resources.getStringArray(R.array.places_name)
    }

    override fun getPersonsNames(): Array<String> {
        Log.i(TAG, this.javaClass.simpleName + ".getPersonsNames")
        return ctx.resources.getStringArray(R.array.persons_name)
    }

    override fun getWeaponsNames(): Array<String> {
        Log.i(TAG, this.javaClass.simpleName + ".getWeaponsNames")
        return ctx.resources.getStringArray(R.array.weapons_name)
    }

}
