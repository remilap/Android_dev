package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.Settings.Companion.players
import com.remilapointe.cluedo.game.Settings.Companion.yourPlayerNumber
import com.remilapointe.cluedo.game.cards.Card
import com.remilapointe.cluedo.game.cards.CardPack
import kotlinx.android.synthetic.main.activity_show_cards2.*

fun Context.showCards2Intent(which: Int): Intent {
    Util.log("intent with which=$which")
    return Intent(this, ShowCards2::class.java).apply {
        putExtra(EXTRA_SHOW_CARDS, which)
    }
}


class ShowCards2 : AppCompatActivity(), View.OnClickListener {

    private var showCards = ArrayList<Card>()
    private var which = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.log("")
        setContentView(R.layout.activity_show_cards2)

        // retrieve intent parameter
        which = this.intent.getIntExtra(EXTRA_SHOW_CARDS, 0)
        Util.log("showCards which: $which")

        // retrieve cards to show
        var txt = ""
//        var showCards: Array<Card> = emptyArray()
        if (which == -1) {
            txt = "Cards of the enigma"
            Util.log("show $txt")
            for (c in CardPack.getSoluce()) showCards.add(c)
//            showCards = CardPack.getSoluce()
        } else if (which < players.size) {
            for (c in players[which].cardsInHand) showCards.add(c)
//            showCards = Settings.players[which].cardsInHand.toTypedArray()
            if (which == yourPlayerNumber) {
                txt = "Your cards"
            } else {
                txt = "Cards of ${players[which].name}"
            }
            Util.log("show $txt, cards number: ${showCards.size}")
        }

        gv_show_cards.adapter = CardImageAdapter(this, showCards)

        bt_show_cards_cancel2.setOnClickListener(this)
        bt_show_cards_ok2.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        Util.log("with view=${view?.id}")
        when (view?.id) {
            R.id.bt_show_cards_ok2 -> {
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            R.id.bt_show_cards_cancel2 -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

}
