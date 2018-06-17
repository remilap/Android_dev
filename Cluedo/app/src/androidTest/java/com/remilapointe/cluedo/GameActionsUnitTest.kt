package com.remilapointe.cluedo

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.remilapointe.cluedo.game.GameActions
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GameActionsUnitTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.remilapointe.cluedo", appContext.packageName)
    }

    @Test
    fun checkInit() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.remilapointe.cluedo", appContext.packageName)

        // check initCards
        var game = GameActions()
        /*
        game.initCards(appContext.resources)
        Assert.assertEquals(game.nbPersons, 6)
        Assert.assertEquals(game.nbWeapons, 6)
        Assert.assertEquals(game.nbPlaces, 9)
        Assert.assertEquals(game.persons.size, game.nbPersons)
        Assert.assertEquals(game.weapons.size, game.nbWeapons)
        Assert.assertEquals(game.places.size, game.nbPlaces)
        Assert.assertEquals(game.cards.size, game.nbPersons + game.nbWeapons + game.nbPlaces)

        // check addPlayer
        var pers1 = game.persons.get(2)
        game.addPlayer("toto", pers1)
        Assert.assertEquals(game.nbPlayers, 1)
        var pl1 = game.players.get(0)
        Assert.assertEquals(pl1.name, "toto")
        Assert.assertEquals(pl1.cPerson.name, pers1.name)

        var pers2 = game.persons.get(4)
        game.addPlayer("titi", pers2)
        Assert.assertEquals(game.nbPlayers, 2)
        var pl2 = game.players.get(1)
        Assert.assertEquals(pl2.name, "titi")
        Assert.assertEquals(pl2.name, pers2.name)

        // check createSolution
        game.createSolution()
        Assert.assertEquals(game.cards.size, game.nbPersons + game.nbWeapons + game.nbPlaces - 3)
        println("solution place: ${game.solution[0].name}")
        println("solution person: ${game.solution[1].name}")
        println("solution weapon: ${game.solution[2].name}")

        // check distributeCards
        game.distributeCards()
        Assert.assertEquals(game.cards.size, 0)
        Assert.assertEquals(pl1.cardsInHand.size, 9)
        Assert.assertEquals(pl2.cardsInHand.size, 9)
        */
    }

}