package com.remilapointe.cluedo.game

import com.remilapointe.cluedo.game.cards.Card

class HelpPlayer {
    var myGame: GameActions = GameActions()
    lateinit var myPlayer: Player
    var myCards: MutableList<Card> = mutableListOf()

    fun providePlayerCards(player: Player, cards: MutableList<Card>) {
        myPlayer = player
        myGame.addPlayer(player)
        myCards = cards
        for (c in cards) myGame.addCardToPlayer(player, c)
    }

    fun suggestion() {

    }
}
