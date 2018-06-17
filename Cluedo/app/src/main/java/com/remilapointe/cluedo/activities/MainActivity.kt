package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.game.*
import com.remilapointe.cluedo.game.Settings.Companion.gameMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        bt_new_alone.setOnClickListener(this)
        bt_new_against_AI.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> optSettings()
            R.id.action_new_alone -> optNewAlone()
            R.id.action_new_against_AI -> optNewAgainstIA()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onClick(p0: View?) {
        Log.i(TAG, "click on "+p0?.id)
        when (p0?.id) {
            R.id.bt_new_alone -> optNewAlone()
            R.id.bt_new_against_AI -> optNewAgainstIA()
            else -> Log.i(TAG, "Pas prévu ni " + R.id.action_settings + ", ni " + R.id.action_new_alone + ", ni " + R.id.action_new_against_AI)
        }
    }

    private fun optSettings() : Boolean {
        Log.i(TAG, "Settings")
        Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
        return true
    }

    private fun optNewAlone() : Boolean {
        Log.i(TAG, "Conseils de jeu")
        gameMode = GAME_MODE_ALONE
        //startActivity(alonePlayerIntent())
        startActivityForResult(inputNbPlayersIntent(), REQ_CODE_INPUT_NB_PLAYER)
        return true
    }

    private fun optNewAgainstIA() : Boolean {
        Log.i(TAG, "Contre IA")
        gameMode = GAME_MODE_HELP
        Toast.makeText(this, "Contre IA", Toast.LENGTH_LONG).show()
        startActivity(selectCardsIntent())
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_INPUT_NB_PLAYER -> {
                if (resultCode == Activity.RESULT_OK) {
//                    gameMode = data?.getIntExtra(EXTRA_GAME_MODE, GAME_MODE_ALONE)!!
//                    nbPlayers = data?.getIntExtra(EXTRA_NB_PLAYERS, 2)!!
                    if (gameMode == GAME_MODE_ALONE) {
                        startActivityForResult(helpPlayerIntent(), REQ_CODE_HELP_PLAYERS)
                    }
                }
            }
            REQ_CODE_HELP_PLAYERS -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "return from HelpPlayers", Toast.LENGTH_LONG).show()
                }
            }
            REQ_CODE_SELECTED_CARDS -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "return from SelectCards", Toast.LENGTH_LONG).show()
                    /*
                    val selectedCards = data?.getStringExtra(SELECTED_CARDS)
                    val selCards = selectedCards?.split(',')
                    if (selCards != null) {
                        for (cs in selCards) {
                            val ci: Int = cs.toInt()
                            println(ci)
                        }
                    }
                    */
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
