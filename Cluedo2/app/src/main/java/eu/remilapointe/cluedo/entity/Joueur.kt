package eu.remilapointe.cluedo.entity

import com.log4k.d

class Joueur(var nom: String) {
    var id = nextId++
    var personnage = Personnage(nom, nom, 0, 0)
    var cartes = mutableListOf<Carte>()
    var cartesMontreesPar = mutableListOf<CarteMontreePar>()

    fun addCarteEnMain(carte: Carte) {
        cartes.add(carte)
        d("add carte en main pour joueur $nom : carte ${carte.nom} => size = ${cartes.size}")
    }

    fun addCarteMontree(carte: Carte, joueur: Joueur) {
        val cMontree = CarteMontreePar(carte, joueur)
        cartesMontreesPar.add(cMontree)
        d("add carte en mains pour joueur $nom : carte ${carte.nom} montree par ${joueur.nom} => size = ${cartesMontreesPar.size}")
    }

    companion object {
        var nextId = 1
    }

}
