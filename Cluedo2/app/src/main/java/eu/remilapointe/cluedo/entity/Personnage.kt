package eu.remilapointe.cluedo.entity

class Personnage(nom: String, coul: String, cx: Int, cy: Int) : Carte(nom) {

    init {
        type = Carte.TYP_PERSONNAGE
    }

    override fun ouEstLaCarte() : Int {
        return 0
    }

}
