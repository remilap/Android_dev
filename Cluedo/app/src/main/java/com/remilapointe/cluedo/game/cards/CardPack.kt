package com.remilapointe.cluedo.game.cards

import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.ResourcesItf
import java.util.*

object CardPack {


    var places: MutableList<Place> = mutableListOf()
    var nbPlaces = 0
    var persons: MutableList<Person> = mutableListOf()
    var nbPersons = 0
    var weapons: MutableList<Weapon> = mutableListOf()
    var nbWeapons = 0
    var cards: MutableList<Card> = mutableListOf()
    var nbCards = 0
    var solution: MutableList<Card> = mutableListOf()
    var nbCardsInSoluc = 0

    init {
        Util.log("Init CardPack")
    }

    fun initCards(res: ResourcesItf) {
        Util.log("")
        if (cards.isNotEmpty()) return

        // create the different Places cards
        val strPlaces = res.getPlacesNames()
        for (p in strPlaces) {
            var pl = Place(p)
            Util.log("add Place ${pl.name}")
            places.add(pl)
            cards.add(pl)
        }
        nbPlaces = places.size
        // create the different Persons cards
        val strPersons = res.getPersonsNames()
        for (p in strPersons) {
            var pe = Person(p)
            Util.log("add Person ${pe.name}")
            persons.add(pe)
            cards.add(pe)
        }
        nbPersons = persons.size
        // create the different Weapons cards
        val strWeapons = res.getWeaponsNames()
        for (w in strWeapons) {
            var we = Weapon(w)
            Util.log("add Weapon ${we.name}")
            weapons.add(we)
            cards.add(we)
        }
        nbWeapons = weapons.size
        nbCards = nbPlaces + nbPersons + nbWeapons
    }

    fun createSolution() {
        Util.log("")
        // create a solution
        val random = Random()
        var sPlace = random.nextInt(places.size)
        var pl = places.get(sPlace)
        pl.where = CARD_IN_SOLUCE
        solution.add(pl)
//        cards.remove(pl)
        var sPerson = random.nextInt(persons.size)
        var pe = persons.get(sPerson)
        pe.where = CARD_IN_SOLUCE
        solution.add(pe)
//        cards.remove(pe)
        var sWeapon = random.nextInt(weapons.size)
        var we = weapons.get(sWeapon)
        we.where = CARD_IN_SOLUCE
        solution.add(we)
//        cards.remove(we)
        nbCardsInSoluc = 3
    }

    fun isInSolution(card: Card): Boolean {
        Util.log("card name: ${card.name}")
        return card.where == CARD_IN_SOLUCE
    }

    fun getCard(name: String): Card? {
        Util.log("card name: $name")
        for (c in cards) {
            if (c.name.equals(name)) {
                return c
            }
        }
        return null
    }

    fun getSoluce(): Array<Card> {
        Util.log("")
        val res = Array<Card>(3) { _ -> Place("")}
        var n = 0
        for (c in solution) res[n++] = c
        return res
    }

}
