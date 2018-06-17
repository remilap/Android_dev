package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.game.Settings.Companion.nbPlayers
import com.remilapointe.cluedo.game.TAG
import kotlinx.android.synthetic.main.input_nb_players.*

fun Context.inputNbPlayersIntent(): Intent {
    Log.i(TAG, this.javaClass.simpleName + ".inputNbPlayersIntent")
    return Intent(this, InputNbPlayers::class.java).apply {
//        putExtra(EXTRA_GAME_MODE, gameMode)
        //addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
    }
}

class InputNbPlayers : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, this.javaClass.simpleName + ".onCreate")
        setContentView(R.layout.input_nb_players)

//        var gameMode = intent.getIntExtra(EXTRA_GAME_MODE, GAME_MODE_ALONE)

        bt_next.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val resultIntent = Intent()
        when (p0?.id) {
            R.id.bt_next -> {
                when (rg_nbplayers.checkedRadioButtonId) {
                    rb_nbpl_2.id -> nbPlayers = 2
                    rb_nbpl_3.id -> nbPlayers = 3
                    rb_nbpl_4.id -> nbPlayers = 4
                    rb_nbpl_5.id -> nbPlayers = 5
                    rb_nbpl_6.id -> nbPlayers = 6
                }
                Toast.makeText(this, "Nb players: $nbPlayers", Toast.LENGTH_LONG).show()
//                resultIntent.putExtra(EXTRA_NB_PLAYERS, nbPlayers)
//                resultIntent.putExtra(EXTRA_GAME_MODE, gameMode)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            R.id.bt_cancel -> {
                nbPlayers = 0
//                resultIntent.putExtra(EXTRA_NB_PLAYERS, 0)
//                resultIntent.putExtra(EXTRA_GAME_MODE, gameMode)
                setResult(Activity.RESULT_CANCELED, resultIntent)
                finish()
            }
        }
    }

}
