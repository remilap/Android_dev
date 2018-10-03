package com.remilapointe.cluedo.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.remilapointe.cluedo.PreferenceHelper
import com.remilapointe.cluedo.PreferenceHelper.get
import com.remilapointe.cluedo.PreferenceHelper.set
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.Util
import com.remilapointe.cluedo.game.GameActions
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_ALONE
import com.remilapointe.cluedo.game.GameItf.Companion.GAME_MODE_HELP
import com.remilapointe.cluedo.game.REQ_CODE_HELP_PLAYERS
import com.remilapointe.cluedo.game.REQ_CODE_INPUT_NB_PLAYER
import com.remilapointe.cluedo.game.REQ_CODE_SELECTED_CARDS
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val USER_KEY = "key_user"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var game: GameActions
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.log("main")
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        bt_new_alone.setOnClickListener(this)
        bt_new_against_AI.setOnClickListener(this)

        prefs = PreferenceHelper.defaultPrefs(this)
        prefs[USER_KEY] = "test"
        val value: String? = prefs[USER_KEY]

        game = GameActions()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Util.log("")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Util.log("${item.itemId}")
        when (item.itemId) {
            R.id.action_settings -> optSettings()
            R.id.action_new_alone -> optNewAlone()
            R.id.action_new_against_AI -> optNewAgainstIA()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onClick(view: View?) {
        Util.log("click on ${view?.id}")
        when (view?.id) {
            R.id.bt_new_alone -> optNewAlone()
            R.id.bt_new_against_AI -> optNewAgainstIA()
            else -> Util.log("Pas prévu ni " + R.id.action_settings + ", ni " + R.id.action_new_alone + ", ni " + R.id.action_new_against_AI)
        }
    }

    private fun optSettings() : Boolean {
        Util.log("Settings")
        Toast.makeText(this, "Settings not implemented", Toast.LENGTH_LONG).show()
        return true
    }

    private fun optNewAlone() : Boolean {
        Util.log("Conseils de jeu")
        game.setGameMode(GAME_MODE_HELP)
        startActivityForResult(inputNbPlayersIntent(), REQ_CODE_INPUT_NB_PLAYER)
        return true
    }

    private fun optNewAgainstIA() : Boolean {
        Util.log("Contre IA")
        game.setGameMode(GAME_MODE_ALONE)
        Toast.makeText(this, "Contre IA", Toast.LENGTH_LONG).show()
        startActivityForResult(inputNbPlayersIntent(), REQ_CODE_INPUT_NB_PLAYER)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Util.log("requestCode: $requestCode, resultCode: $resultCode, gameMode: ${game.getGameMode()}")
        when (requestCode) {
            REQ_CODE_INPUT_NB_PLAYER -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (game.getGameMode() == GAME_MODE_HELP) {
                        startActivityForResult(inputPlayersIntent(), REQ_CODE_HELP_PLAYERS)
                    } else if (game.getGameMode() == GAME_MODE_ALONE) {
                        startActivityForResult(inputPlayersIntent(), REQ_CODE_HELP_PLAYERS)
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
