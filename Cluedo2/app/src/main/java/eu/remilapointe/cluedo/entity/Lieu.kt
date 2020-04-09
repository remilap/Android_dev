package eu.remilapointe.cluedo.entity

class Lieu(nom: String) : Carte(nom) {

    init {
        type = Carte.TYP_LIEU
    }

    override fun ouEstLaCarte(): Int {
        return 0
    }

}
