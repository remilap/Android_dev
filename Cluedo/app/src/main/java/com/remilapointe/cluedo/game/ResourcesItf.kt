package com.remilapointe.cluedo.game

interface ResourcesItf {
    fun getPlacesNames(): Array<String>
    fun getPersonsNames(): Array<String>
    fun getWeaponsNames(): Array<String>
    fun getAllCards(): Array<String>
}