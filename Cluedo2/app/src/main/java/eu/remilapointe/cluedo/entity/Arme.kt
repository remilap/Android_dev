package eu.remilapointe.cluedo.entity

class Arme(nom: String) : Carte(nom) {

    init {
        type = Carte.TYP_ARME
    }

    override fun ouEstLaCarte(): Int {
        return 0
    }
}
