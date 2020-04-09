package eu.remilapointe.cluedo.entity

import android.content.res.Resources
import com.log4k.d

open class JeuActif(val res: Resources) {

    var listPers = mutableListOf<Personnage>()
    var listArme = mutableListOf<Arme>()
    var listLieu = mutableListOf<Lieu>()

    var listJoueurs = mutableListOf<Joueur>()
    var listCartes = mutableListOf<Carte>()
    var listEnigme = mutableListOf<Carte>()

    fun addJoueur(nom: String) : Int {
        val j = Joueur(nom)
        listJoueurs.add(j)
        d("add Joueur ${j.nom} avec id=${j.id} => size = ${listJoueurs.size}")
        return j.id
    }

    fun addPers(pers: Personnage) : Int {
        listPers.add(pers)
        d("add Pers ${pers.nom} => size = ${listPers.size}")
        return listPers.size
    }

    fun addArme(arme: Arme) : Int {
        listArme.add(arme)
        d("add Arme ${arme.nom} => size = ${listArme.size}")
        return listArme.size
    }

    fun addLieu(lieu: Lieu) : Int {
        listLieu.add(lieu)
        d("add Lieu ${lieu.nom} => size = ${listLieu.size}")
        return listLieu.size
    }

    fun addCarte(carte: Carte) : Int {
        listCartes.add(carte)
        d("add Carte ${carte.nom} => size = ${listCartes.size}")
        return listCartes.size
    }

    fun addEnigme(carte: Carte) : Int {
        listEnigme.add(carte)
        carte.inEnigme = true
        d("add Enigme ${carte.nom} => size = ${listEnigme.size}")
        return listEnigme.size
    }

    fun distribution() {
        // select 1 card of each type for the enigma
        val rndPers = (1..listPers.size).random() - 1
        val rndArme = (1..listArme.size).random() - 1
        val rndLieu = (1..listLieu.size).random() - 1
        val cPers = listPers[rndPers]
        addEnigme(cPers)
        val cArme = listArme[rndArme]
        addEnigme(cArme)
        val cLieu = listLieu[rndLieu]
        addEnigme(cLieu)

        // add all cards in the game
        listPers.forEach { if (! it.inEnigme) addCarte(it) }
        listArme.forEach { if (! it.inEnigme) addCarte(it) }
        listLieu.forEach { if (! it.inEnigme) addCarte(it) }

        // distribute cards to all players
        var j = 0
        while (listCartes.isNotEmpty()) {
            d("pour joueur no $j, list size : ${listJoueurs.size}")
            val joueur = listJoueurs[j]
            d("pour joueur no $j, ${joueur.nom}")
            val rndCarte = (1..listCartes.size).random() - 1
            val carte = listCartes[rndCarte]
            d("random carte no $rndCarte, ${carte.nom}")
            carte.inJoueur = j
            joueur.addCarteEnMain(carte)
            listCartes.remove(carte)
            d("carte ${carte.nom} retiree => size = ${listCartes.size}")
            j = if (j < listJoueurs.size - 1) j+1 else 0
        }

    }

}
