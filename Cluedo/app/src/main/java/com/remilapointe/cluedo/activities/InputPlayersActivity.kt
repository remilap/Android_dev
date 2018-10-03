package com.remilapointe.cluedo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.ResourcesAndroid
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_ALONE
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_HELP
import com.remilapointe.cluedo.game.MAX_PLAYERS
import com.remilapointe.cluedo.game.ResourcesItf
import com.remilapointe.cluedo.game.Settings.Companion.game
import com.remilapointe.cluedo.game.Settings.Companion.gameMode
import com.remilapointe.cluedo.game.Settings.Companion.nbPlayers
import com.remilapointe.cluedo.game.Settings.Companion.yourPlayerNumber
import com.remilapointe.cluedo.game.TAG
import com.remilapointe.cluedo.game.cards.CardPack
import kotlinx.android.synthetic.main.activity_input_players.*

fun Context.inputPlayersIntent(): Intent {
    Util.log("intent")
    return Intent(this, InputPlayersActivity::class.java).apply {
//        putExtra(EXTRA_NB_PLAYERS, nbPlayers)
    }
}

class InputPlayersActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener, TextWatcher {

    private lateinit var edTxTab: Array<EditText>
    private lateinit var spinTab: Array<Spinner>
    private lateinit var okBut: Button
    private var playerId: Array<Int> = Array(MAX_PLAYERS + 1) { _ -> 0}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.log("with gameMode=$gameMode and nbPlayers=$nbPlayers")
        setContentView(R.layout.activity_input_players)
        //setSupportActionBar(toolbar)

        // init cards
        Util.log("before initCards")
        var res: ResourcesItf = ResourcesAndroid(this.applicationContext)
        CardPack.initCards(res)

        // retrieve spinner your player number
        val spin1 = findViewById<Spinner>(R.id.spin_your_player_number)
        spin1.onItemSelectedListener = this
        val aa1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Array(nbPlayers) { i -> i+1})
        spin1.adapter = aa1

        // add rows for players' name and person
        edTxTab = Array(MAX_PLAYERS+1) { _ -> EditText(this) }
        spinTab = Array(MAX_PLAYERS+1) { _ -> Spinner(this)}
        val trOkCancel = findViewById<TableRow>(R.id.tr_ok_cancel_players)
        val tab1 = findViewById<TableLayout>(R.id.tl_players_name)
        tab1.removeView(trOkCancel)
        for (i in 1..nbPlayers) {
            var newTabRow = TableRow(this)
            newTabRow.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            var newTextView = TextView(this)
            newTextView.text = getString(R.string.str_player_name, i)
            newTextView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            newTabRow.addView(newTextView)
            edTxTab[i].layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            edTxTab[i].id = View.generateViewId()
            Util.log("ed_id[$i]: ${edTxTab[i].id}")
            edTxTab[i].setOnClickListener(this)
            edTxTab[i].addTextChangedListener(this)
            newTabRow.addView(edTxTab[i])
            tab1.addView(newTabRow)

            newTabRow = TableRow(this)
            newTabRow.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            newTextView = TextView(this)
            newTextView.text = getString(R.string.str_game_character, i)
            newTextView.layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            newTabRow.addView(newTextView)
            spinTab[i].layoutParams = TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT)
            spinTab[i].id = View.generateViewId()
            Util.log("sp_id[$i]: ${spinTab[i].id}")
            spinTab[i].onItemSelectedListener = this
            val aa2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, res.getPersonsNames())
            spinTab[i].adapter = aa2
            newTabRow.addView(spinTab[i])
            tab1.addView(newTabRow)
        }
        tab1.addView(trOkCancel)

        // retrieve ok button
        okBut = findViewById(R.id.bt_add_player_ok)
        okBut.setOnClickListener(this)
        okBut.isEnabled = false

        // retrieve cancel button
        bt_add_player_cancel.setOnClickListener(this)
    }

    override fun afterTextChanged(p0: Editable?) {
        Util.log("")
        decideOkIsPossible()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Util.log("")
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Util.log("")
    }

    override fun onClick(p0: View?) {
        Util.log("with p0=${p0?.id}")
        when {
            p0?.id ==  R.id.bt_add_player_ok -> {
                for (i in 1..nbPlayers) {
                    var playerName = findViewById<EditText>(edTxTab[i].id).text.toString().trim()
                    var pers = CardPack.persons[playerId[i]]
                    Util.log("i=$i, player name=$playerName, personage: ${pers.name}")
                    game.addPlayer(playerName, pers)
                }
                this.finish()
                if (game.getGameMode() == GAME_MODE_HELP) {
                    startActivity(selectCardsIntent(true))
                } else if (game.getGameMode() == GAME_MODE_ALONE) {
                    game.distributeCards()
                    startActivity(gameTurnsIntent())
                }
            }
            p0?.id == R.id.bt_add_player_cancel -> {
                this.finish()
            }
            else -> {
                Util.log("unknown button ${p0?.id}")
            }
        }
    }

    private fun decideOkIsPossible() {
        Util.log("")
        var count = 0
        for (i in 1..nbPlayers) {
            if (edTxTab[i].text.isNotEmpty()) {
                count++
                Util.log("player $i name is not empty => count=$count")
                for (j in i+1..nbPlayers) {
                    Log.i(TAG, "compare name $i (${edTxTab[i].text.trim()}) and $j (${edTxTab[j].text.trim()})")
                    if (edTxTab[i].text.trim() == edTxTab[j].text.trim()) {
                        count--
                        Util.log("player $i name is the same than player $j => count=$count")
                        break
                    }
                }
            } else {
                break
            }
        }
        for (i in 1..nbPlayers) {
            for (j in i+1..nbPlayers) {
                if (spinTab[i].selectedItemPosition == spinTab[j].selectedItemPosition) {
                    count--
                    Util.log("player $i person is the same than player $j => count=$count")
                    break
                }
            }
        }
        Util.log("fin des comptes => count=$count")
        okBut.isEnabled = (count == nbPlayers)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Util.log("")
        decideOkIsPossible()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Util.log("with p2=$p2")
        var found = false
        for (i in 1..nbPlayers) {
            if (p0?.id == spinTab[i].id) {
                //var pers = findViewById<EditText>(sp_id[i]).text.toString()
                playerId[i] = p2
                Util.log("for player $i, selected person: ${CardPack.persons[p2].name}")
                found = true
                break
            }
        }
        if (! found) {
            if (p0?.id == R.id.spin_your_player_number) {
                yourPlayerNumber = p2
                Util.log("your player number: $yourPlayerNumber")
            } else {
                Util.log("unknown spinner ${p0?.id}")
            }
        }
        decideOkIsPossible()
    }

}
