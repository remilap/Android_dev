package com.remilapointe.cluedo.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.R.layout.dialog_select_num_player
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_ALONE
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_HELP
import com.remilapointe.cluedo.game.REQ_CODE_SELECTED_CARDS
import com.remilapointe.cluedo.game.Settings.Companion.gameMode
import com.remilapointe.cluedo.game.Settings.Companion.nbPlayers
import com.remilapointe.cluedo.game.Settings.Companion.playerTurn
import com.remilapointe.cluedo.game.Settings.Companion.players
import com.remilapointe.cluedo.game.Settings.Companion.yourPlayerNumber
import com.remilapointe.cluedo.game.cards.CardPack

fun Context.gameTurnsIntent(): Intent {
    Util.log("intent")
    return Intent(this, GameTurnsActivity::class.java).apply {
        //addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
    }
}

class GameTurnsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private lateinit var tvCardsSoluce: TextView
    private lateinit var spMenuTurn: Spinner
    private lateinit var aa1: ArrayAdapter<String>
    private lateinit var vg: ViewGroup
    private lateinit var selPlNumberDialog: AlertDialog
    private var idChoiceList: ArrayList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alone_player)

        tvCardsSoluce = findViewById(R.id.tv_cards_in_soluce)
        var txt = getString(R.string.str_cards_in_soluce)
        for (card in CardPack.getSoluce()) {
            txt += System.lineSeparator() + card.name
        }
        Util.log("soluce: $txt")
        tvCardsSoluce.text = txt

        spMenuTurn = findViewById(R.id.sp_menu_turn)
        spMenuTurn.onItemSelectedListener = this
        aa1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListOf("Choose"))
        aa1.clear()
        aa1.addAll(setChoiceList())
        spMenuTurn.adapter = aa1

        findViewById<Button>(R.id.bt_quit).setOnClickListener(this)
    }

    private fun setChoiceList(): MutableList<String> {
        var idMenu = 0
        when (gameMode) {
            GAME_MODE_ALONE -> {
                when (playerTurn) {
                    yourPlayerNumber -> idMenu = R.array.play_menu_alone_my_turn
                    else -> idMenu = R.array.play_menu_alone_turn_other
                }
            }
            GAME_MODE_HELP -> {
                when (playerTurn) {
                    yourPlayerNumber -> idMenu = R.array.play_menu_advice_my_turn
                    else -> idMenu = R.array.play_menu_advice_turn_other
                }
            }
        }
        var resList = mutableListOf<String>()
        val choiceList = resources.getStringArray(R.array.play_menu_base) + resources.getStringArray(idMenu)
        for ((i, c) in choiceList.withIndex()) {
            val sp = c.split(" ")
            idChoiceList.add(sp[0].toInt())
            resList.add( choiceList[i].replace("${sp[0]} ", "") )
            Util.log("choice $i: ${resList[i]}")
        }
        return resList
    }

    override fun onClick(view: View?) {
        Util.log("with view=${view?.id}")
        when (view?.id) {
            R.id.bt_quit -> {
                finish()
            }
        }
    }

    override fun onItemClick(adapterView: AdapterView<*>?, v: View?, position: Int, pos: Long) {
        Util.log("not implemented")
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        Util.log("not implemented")
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, pos: Long) {
        Util.log("with view.id=${adapterView?.id} and position=$position")
        when (adapterView?.id) {
            R.id.sp_menu_turn -> {
                val posId = idChoiceList[position]
                Util.log("posId: $posId")
                when (posId) {
                    0 -> {
                        // action bidon
                        Util.log("choisir une action")
                    }
                    1 -> {
                        // voir mes cartes
                        var txt = getString(R.string.str_cards_in_your_hand)
                        Util.log("your player number: ${yourPlayerNumber}")
                        val mListCards = players[yourPlayerNumber].cardsInHand
                        for (card in mListCards) {
                            txt += System.lineSeparator() + card.name
                        }
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                        startActivity(showCardsIntent(yourPlayerNumber))
                    }
                    2 -> {
                        var txt = "View the known cards of another player"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                        var which = 1
                        if (nbPlayers == 2) {
                            if (yourPlayerNumber == 1) {
                                which = 2
                                startActivity(showCardsIntent(which))
                            }
                        } else {
                            var selPl = ArrayList<String>(nbPlayers)
                            selPl.add(getString(R.string.str_select_a_player))
                            for (i in 0 until nbPlayers) {
                                if (i != yourPlayerNumber) selPl.add(players[i].name)
                            }
                            selectPlayerNumberDialog(getString(R.string.str_select_a_player), selPl)
                        }
                    }
                    3 -> {
                        var txt = "Ask for help to make an hypothesis"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                        playerTurn++
                        if (playerTurn > nbPlayers) playerTurn = 0
                    }
                    4 -> {
                        var txt = "Make an hypothesis and its answer"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                        gameMode = 3 - gameMode
                    }
                    5 -> {
                        var txt ="Make an accusation and its answer"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                    6 -> {
                        var txt = "Ask for help to answer to an hypothesis of a player"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                    7 -> {
                        var txt = "Answer of one player to an hypothesis of another player"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                    8 -> {
                        var txt = "I make an hypothesis and get the answer"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                    9 -> {
                        var txt = "I make an accusation and get the answer"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                    10 -> {
                        var txt = "Show the player action"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        var txt = "Unknown option"
                        Util.log(txt)
                        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    }
                }
                aa1.clear()
                aa1.addAll(setChoiceList())
                adapterView.setSelection(0)
            }

            R.id.sp_sel_num_pl -> {
                if (position > 0) {
                    var which = position
                    // voir les cartes connues d'un autre joueur
                    var txt = getString(R.string.str_known_cards, players[which].name)
                    val mListCards = players[which].cardsInHand
                    for (card in mListCards) {
                        txt += System.lineSeparator() + card.name
                    }
                    Util.log(txt)
                    Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
                    selPlNumberDialog.dismiss()
                    startActivity(showCardsIntent(which))
                }
            }
        }
    }

    private fun selectPlayerNumberDialog(title: String, numList: ArrayList<String>) {
        Util.log("taille liste: ${numList.size}")
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(dialog_select_num_player, null)
        dialogBuilder.setView(dialogView)

        val spinSel = dialogView.findViewById<Spinner>(R.id.sp_sel_num_pl)
        val aa = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, numList)
        spinSel.adapter = aa
        spinSel.onItemSelectedListener = this
        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(getString(R.string.str_select_a_player))
        selPlNumberDialog = dialogBuilder.create()
        selPlNumberDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Util.log("requestCode: $requestCode, resultCode: $resultCode")
        when (requestCode) {
            REQ_CODE_SELECTED_CARDS -> {
                if (resultCode == Activity.RESULT_OK) {/*
                    val selectedCards = data?.getStringExtra(SELECTED_CARDS)
                    val selCards = selectedCards?.split(',')
                    if (selCards != null) {
                        for (cs in selCards) {
                            val ci: Int = cs.toInt()

                        }
                    }
                */}
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
