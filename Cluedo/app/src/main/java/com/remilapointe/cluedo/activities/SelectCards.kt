package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.Settings.Companion.game
import com.remilapointe.cluedo.game.Settings.Companion.players
import com.remilapointe.cluedo.game.Settings.Companion.yourPlayerNumber
import com.remilapointe.cluedo.game.cards.CardPack
import kotlin.math.max

fun Context.selectCardsIntent(multiple: Boolean): Intent {
    Util.log("intent")
    return Intent(this, SelectCards::class.java).apply {
        putExtra(EXTRA_MULTIPLE_CARDS, multiple)
//        addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
    }
}

const val EXTRA_MULTIPLE_CARDS = "multiple_cards"

class SelectCards : AppCompatActivity(), View.OnClickListener {

    private lateinit var cbxCards: Array<CheckBox>

    private var multi = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.log("")
        setContentView(R.layout.activity_select_card)

        // retrieve intent parameter
        multi = this.intent.getBooleanExtra(EXTRA_MULTIPLE_CARDS, true)

        // populate tableLayout with check boxes
        cbxCards = Array(CardPack.nbCards) {_ -> CheckBox(this) }
        val trOkCancel = findViewById<TableRow>(R.id.tr_ok_cancel_cards)
        val tab1 = findViewById<TableLayout>(R.id.tl_select_cards)
        tab1.removeView(trOkCancel)
        var maxlines = max(CardPack.places.size, max(CardPack.persons.size, CardPack.weapons.size)) - 1
        for (i in 0..maxlines) {
            var newTabRow = TableRow(this)
            newTabRow.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            // add a place
            if (i < CardPack.places.size) {
                cbxCards[i].layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
                cbxCards[i].id = View.generateViewId()
                cbxCards[i].setOnClickListener(this)
                Util.log("checkbox place $i, id=${cbxCards[i].id}")
                cbxCards[i].text = CardPack.places[i].name
                newTabRow.addView(cbxCards[i])
            } else {
                val txView = TextView(this)
                txView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
                txView.text = ""
                newTabRow.addView(txView)
            }
            // add a person
            if (i < CardPack.persons.size) {
                var n = CardPack.places.size + i
                cbxCards[n].layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
                cbxCards[n].id = View.generateViewId()
                cbxCards[n].setOnClickListener(this)
                Util.log("checbox person $i, id=${cbxCards[n].id}")
                cbxCards[n].text = CardPack.persons[i].name
                newTabRow.addView(cbxCards[n])
            } else {
                val txView = TextView(this)
                txView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
                txView.text = ""
                newTabRow.addView(txView)
            }
            // add a weapon
            if (i < CardPack.weapons.size) {
                var n = CardPack.places.size + CardPack.persons.size + i
                cbxCards[n].layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
                cbxCards[n].id = View.generateViewId()
                cbxCards[n].setOnClickListener(this)
                Util.log("checbox weapon $i, id=${cbxCards[n].id}")
                cbxCards[n].text = CardPack.weapons[i].name
                newTabRow.addView(cbxCards[n])
            } else {
                val txView = TextView(this)
                txView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
                txView.text = ""
                newTabRow.addView(txView)
            }
            tab1.addView(newTabRow)
        }
        tab1.addView(trOkCancel)

        findViewById<Button>(R.id.bt_add_cards_ok).setOnClickListener(this)
        findViewById<Button>(R.id.bt_add_cards_cancel).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        Util.log("with p0: ${p0?.id}")
        when (p0?.id) {
            R.id.bt_add_cards_ok -> {
                var msg = ""
                var sep = ""
                val player = players.get(yourPlayerNumber)
                for (i in 0 until cbxCards.size) if (cbxCards[i].isChecked) {
                    msg += sep
                    msg += cbxCards[i].text
                    sep = ", "
                    game.addCardToPlayer(player, CardPack.cards[i])
                }
                Toast.makeText(this, "Selected cards: $msg", Toast.LENGTH_LONG).show()
                Util.log("Selected cards: $msg")
//                val resultIntent = Intent()
//                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                startActivity(gameTurnsIntent())
            }
            R.id.bt_add_cards_cancel -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
        if (! multi) {
            for (i in 0 until CardPack.nbCards) {
                if (p0?.id == cbxCards[i].id) {
                    if (cbxCards[i].isChecked) {
                        if (i < CardPack.nbPlaces) {
                            for (j in 0 until CardPack.nbPlaces) {
                                if (i != j) {
                                    cbxCards[j].isChecked = false
                                }
                            }
                        } else if (i < CardPack.nbPlaces+CardPack.nbPersons) {
                            for (j in CardPack.nbPlaces until CardPack.nbPlaces+CardPack.nbPersons) {
                                if (i != j) {
                                    cbxCards[j].isChecked = false
                                }
                            }
                        } else {
                            for (j in CardPack.nbPlaces+CardPack.nbPersons until CardPack.nbCards) {
                                if (i != j) {
                                    cbxCards[j].isChecked = false
                                }
                            }
                        }
                    }
                    break
                }
            }
        }
    }

}
