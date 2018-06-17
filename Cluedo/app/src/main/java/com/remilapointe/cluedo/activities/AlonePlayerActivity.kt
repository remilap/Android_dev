package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.game.*
import com.remilapointe.cluedo.game.cards.CardPack
import kotlinx.android.synthetic.main.activity_alone_player.*

fun Context.alonePlayerIntent(): Intent {
    Log.i(TAG, this.javaClass.simpleName + ".AlonePlayerIntent")
    return Intent(this, AlonePlayerActivity::class.java).apply {
        //addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
    }
}

class AlonePlayerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    lateinit var tvCardsSoluce: TextView
    lateinit var spMenuTurn: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alone_player)
        Log.i(this::class.java.simpleName, "onCreate")

        tvCardsSoluce = findViewById(R.id.tv_cards_in_soluce)
        var txt = getString(R.string.str_cards_in_soluce)
        for (card in CardPack.getSoluce()) {
            txt += System.lineSeparator() + card.name
        }
        tvCardsSoluce.text = txt

        spMenuTurn = findViewById(R.id.sp_menu_turn)
        spMenuTurn.onItemSelectedListener = this
        val choiceList = arrayOf("Voir les cartes d'un autre joueur", "Voir mes cartes")
        val aa1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choiceList)
        spMenuTurn.adapter = aa1

        bt_quit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.bt_quit -> {
                finish()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.i(TAG, "onNothingSelected not implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.i(TAG, "onItemSelected not implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
