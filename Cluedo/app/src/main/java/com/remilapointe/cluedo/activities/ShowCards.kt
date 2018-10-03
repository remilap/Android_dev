package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.Settings.Companion.players
import com.remilapointe.cluedo.game.Settings.Companion.yourPlayerNumber
import com.remilapointe.cluedo.game.cards.Card
import com.remilapointe.cluedo.game.cards.CardPack
import kotlinx.android.synthetic.main.activity_show_cards.*

fun Context.showCardsIntent(which: Int): Intent {
    Util.log("intent with which=$which")
    return Intent(this, ShowCards::class.java).apply {
        putExtra(EXTRA_SHOW_CARDS, which)
    }
}

const val EXTRA_SHOW_CARDS = "extra_show_cards"

class ShowCards : AppCompatActivity(), View.OnClickListener {

    private lateinit var lstCards: Array<ImageView>
    private var which = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.log("")
        setContentView(R.layout.activity_show_cards)

        // retrieve intent parameter
        which = this.intent.getIntExtra(EXTRA_SHOW_CARDS, 0)
        Util.log("showCards which: $which")

        // populate tableLayout with radio buttons
        var txt = ""
        var showCards: Array<Card> = emptyArray()
        if (which == -1) {
            txt = "Cards of the enigma"
            Util.log("show $txt")
            showCards = CardPack.getSoluce()
        } else if (which < players.size) {
            showCards = players[which].cardsInHand.toTypedArray()
            if (which == yourPlayerNumber) {
                txt = "Your cards"
            } else {
                txt = "Cards of ${players[which].name}"
            }
            Util.log("show $txt, cards number: ${showCards.size}")
        }
        tv_title_show_cards.text = txt

        lstCards = Array(showCards.size) { _ -> ImageView(this) }
        val trOkCancel = tr_ok_cancel_show
        val tab1 = tl_show_cards
//        tab1.removeView(trOkCancel)

        // set title
        var newTabRow = LinearLayout(this)
        /*
        var txtView = TextView(this)
        txtView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
        txtView.text = txt
        newTabRow.orientation = LinearLayout.HORIZONTAL
        newTabRow.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
        newTabRow.addView(txtView)
        tab1.addView(newTabRow)
        */

        // get screen width
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var screenWidth = displayMetrics.widthPixels

        // add lines in a ScrollView
        /*
        val scrView = ScrollView(this)
        scrView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
        tab1.addView(scrView)
        */

        var n = 0
        for (i in 0 until showCards.size) {
            Util.log("n=$n, i=$i, card=${showCards[i].name}")
            if (n == 0) {
                newTabRow = LinearLayout(this)
                newTabRow.orientation = LinearLayout.HORIZONTAL
                newTabRow.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            }
            // add a card
            var iv = lstCards[i]
            iv.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            iv.setPadding(5, 5, 5, 5)
            iv.setBackgroundColor(Color.BLACK)
            iv.id = View.generateViewId()
            Util.log("ImageView place $i, id=${iv.id}")
            var imgName = "cluedo_"
            if (showCards[i].idx < CardPack.nbPlaces) {
                imgName += "rooms2_${showCards[i].idx + 1}"
            } else if (showCards[i].idx < CardPack.nbPlaces + CardPack.nbPersons) {
                imgName += "people2_${showCards[i].idx - CardPack.nbPlaces + 1}"
            } else {
                imgName += "arm2_${showCards[i].idx - CardPack.nbPlaces - CardPack.nbPersons + 1}"
            }
            imgName += "_en"
            Util.log("imgName: $imgName")
            var id = resources.getIdentifier(imgName, "drawable", this.packageName)
            Util.log("img id: $id")
            var dr = getDrawable(id)
//            var v: View = tab1.parent
            iv.setImageDrawable(dr)
            val viewWidthToBitmapWidthRatio: Double = (newTabRow.measuredWidth).toDouble() / 3 / (dr.intrinsicWidth).toDouble()
//            iv.layoutParams.height = (dr.intrinsicHeight * viewWidthToBitmapWidthRatio).toInt()
            iv.layoutParams.width = screenWidth / 3
            Util.log("tabRow width=${newTabRow.width}, img width=${dr.intrinsicWidth}, img heigth=${dr.intrinsicHeight}, ratio=$viewWidthToBitmapWidthRatio")
            iv.scaleType = ImageView.ScaleType.FIT_CENTER
            iv.adjustViewBounds = true
            iv.contentDescription = showCards[i].name
            lstCards[i] = iv
            newTabRow.addView(lstCards[i])
            if (n++ == 2) {
                n = 0
                tab1.addView(newTabRow)
            }
        }
        if (n > 0) {
            tab1.addView(newTabRow)
        }
        ll_main_show_cards.removeView(trOkCancel)
        tab1.addView(trOkCancel)

        bt_show_cards_ok.setOnClickListener(this)
        bt_show_cards_cancel.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        Util.log("with view=${view?.id}")
        when (view?.id) {
            R.id.bt_show_cards_ok -> {
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            R.id.bt_show_cards_cancel -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

}
