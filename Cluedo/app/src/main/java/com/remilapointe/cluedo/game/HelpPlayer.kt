package com.remilapointe.cluedo.game

import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.cards.Card

class HelpPlayer {
    var myGame: GameActions = GameActions()
    lateinit var myPlayer: Player
    var myCards: MutableList<Card> = mutableListOf()

    fun providePlayerCards(player: Player, cards: MutableList<Card>) {
        Util.log("player: ${player.name}, nb cards: ${cards.size}")
        myPlayer = player
        myGame.addPlayer(player)
        myCards = cards
        for (c in cards) myGame.addCardToPlayer(player, c)
    }

    fun suggestion() {
        Util.log("")
    }
}
