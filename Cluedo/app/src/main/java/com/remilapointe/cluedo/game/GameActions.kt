package com.remilapointe.cluedo.game

import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_ALONE
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_HELP
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_UNKNOWN
import com.remilapointe.cluedo.game.Settings.Companion.gameMode
import com.remilapointe.cluedo.game.Settings.Companion.players
import com.remilapointe.cluedo.game.cards.CARD_IN_PLAYER_HAND
import com.remilapointe.cluedo.game.cards.CARD_IN_UNKNOWN
import com.remilapointe.cluedo.game.cards.Card
import com.remilapointe.cluedo.game.cards.CardPack
import java.util.*

class GameActions: GameItf {

    override fun setGameMode(mode: Int) {
        if (mode in GAME_MODE_ALONE..GAME_MODE_HELP) {
            gameMode = mode
        } else {
            gameMode = GAME_MODE_UNKNOWN
        }
    }

    override fun getGameMode(): Int {
        return gameMode
    }

    fun addPlayer(name: String, card: Card) {
        Util.log("name: $name, personage: ${card.name}")
        addPlayer(Player(name, card))
    }

    fun addPlayer(pl: Player) {
        Util.log("player: ${pl.name}")
        if (players.size < MAX_PLAYERS) players.add(pl)
        else Util.log("nb max players ($MAX_PLAYERS) reached")
    }

    fun addCardToPlayer(player: Player, card: Card) {
        Util.log("player: ${player.name} (${player.idx}), card: ${card.name}")
        if (card.where == CARD_IN_UNKNOWN) {
            player.cardsInHand.add(card)
            card.where = CARD_IN_PLAYER_HAND + player.idx
//        CardPack.cards.remove(card)
        }
    }

    fun distributeCards() {
        Util.log("with ${players.size} player(s)")
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
