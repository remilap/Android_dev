package com.remilapointe.cluedo.game

abstract class ResourcesAbstract: ResourcesItf {
    override fun getAllCards(): Array<String> {
        return getPlacesNames()+getPersonsNames()+getWeaponsNames()
    }

}