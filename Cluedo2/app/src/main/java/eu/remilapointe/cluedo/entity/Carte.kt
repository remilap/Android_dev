package eu.remilapointe.cluedo.entity

open class Carte(var nom: String) : CarteITF {
    companion object {
        const val TYP_INIT = 0
        const val TYP_LIEU = 1
        const val TYP_PERSONNAGE = 2
        const val TYP_ARME = 3
        const val IN_INIT = -1
        const val IN_ENIGME = 10
    }
    var type: Int = TYP_INIT
    var inEnigme = false
    var inJoueur = IN_INIT

    override fun ouEstLaCarte() : Int {
        if (inEnigme) {
            return IN_ENIGME
        }
        if (inJoueur >= 0) {
            return inJoueur
        }
        return IN_INIT
    }

}
