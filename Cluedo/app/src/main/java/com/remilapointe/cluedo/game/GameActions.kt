package com.remilapointe.cluedo.game

import com.remilapointe.cluedo.game.cards.CARD_IN_PLAYER_HAND
import com.remilapointe.cluedo.game.cards.CARD_IN_UNKNOWN
import com.remilapointe.cluedo.game.cards.Card
import com.remilapointe.cluedo.game.cards.CardPack
import java.util.*

class GameActions {

    val nbMaxPlayers: Int = 5
    var players: MutableList<Player> = mutableListOf()

    fun addPlayer(name: String, card: Card) {
        players.add(Player(name, card))
    }

    fun addPlayer(pl: Player) {
        if (players.size < nbMaxPlayers) players.add(pl)
    }

    fun addCardToPlayer(player: Player, card: Card) {
        if (card.where != CARD_IN_UNKNOWN) {
            player.cardsInHand.add(card)
            card.where = CARD_IN_PLAYER_HAND + player.idx
//        CardPack.cards.remove(card)
        }
    }

    fun distributeCards() {
        // distribute the cards to n players
        val random = Random()
        var iPlayer = 0
        while (CardPack.cards.size > 0) {
            var sCard = random.nextInt(CardPack.cards.size)
            addCardToPlayer(players.get(iPlayer), CardPack.cards.get(sCard))
            iPlayer++
            if (iPlayer == players.size) iPlayer = 0
        }
    }


}
