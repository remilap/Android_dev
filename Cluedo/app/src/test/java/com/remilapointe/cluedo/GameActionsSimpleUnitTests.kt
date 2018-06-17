package com.remilapointe.cluedo

import com.remilapointe.cluedo.game.GameActions
import com.remilapointe.cluedo.game.ResourcesAbstract
import com.remilapointe.cluedo.game.ResourcesItf
import org.junit.Test

class GameActionsSimpleUnitTests {
    class ResourcesImpl : ResourcesItf, ResourcesAbstract() {
        override fun getPlacesNames(): Array<String> {
            return arrayOf("véranda", "hall", "studio", "salle à manger", "bibliothèque", "bureau", "cuisine", "grand salon", "petit salon")
        }

        override fun getPersonsNames(): Array<String> {
            return arrayOf("Mlle Rose", "Prof. Violet", "Colonel Moutarde", "Mme Pervenche", "Mme Leblanc", "Dc Olive")
        }

        override fun getWeaponsNames(): Array<String> {
            return arrayOf("corde", "révolver", "chandelier", "poignard", "matraque", "autre")
        }
    }

    @Test
    fun checkGame() {
        // check initCards
        var game = GameActions()
        var res = ResourcesImpl()
        /*
        game.initCards(res)
        Assert.assertEquals(game.persons.size, 6)
        Assert.assertEquals(game.weapons.size, 6)
        Assert.assertEquals(game.places.size, 9)
        Assert.assertEquals(game.cards.size, game.persons.size + game.weapons.size + game.places.size)

        // check addPlayer
        var pers1 = game.persons.get(2)
        game.addPlayer("toto", pers1)
        Assert.assertEquals(game.players.size, 1)
        var pl1 = game.players.get(0)
        Assert.assertEquals(pl1.name, "toto")
        Assert.assertEquals(pl1.cPerson.name, pers1.name)

        var pers2 = game.persons.get(4)
        game.addPlayer("titi", pers2)
        Assert.assertEquals(game.players.size, 2)
        var pl2 = game.players.get(1)
        Assert.assertEquals(pl2.name, "titi")
        Assert.assertEquals(pl2.cPerson.name, pers2.name)

        // check createSolution
        game.createSolution()
        Assert.assertEquals(game.cards.size, game.persons.size + game.weapons.size + game.places.size - 3)
        println("solution place: ${game.solution[0].name}")
        println("solution person: ${game.solution[1].name}")
        println("solution weapon: ${game.solution[2].name}")

        // check distributeCards
        game.distributeCards()
        Assert.assertEquals(game.cards.size, 0)
        Assert.assertEquals(pl1.cardsInHand.size, 9)
        Assert.assertEquals(pl2.cardsInHand.size, 9)
        print("cards of ${pl1.name}:")
        for (c in pl1.cardsInHand) print(" ${c.name},")
        println()
        print("cards of ${pl2.name}:")
        for (c in pl2.cardsInHand) print(" ${c.name},")
        println()
        */
    }
}
