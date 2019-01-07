package eu.remilapointe.tictactoe.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import eu.remilapointe.tictactoe.R
import eu.remilapointe.tictactoe.databinding.ActivityGameBinding
import eu.remilapointe.tictactoe.model.Player
import eu.remilapointe.tictactoe.viewmodel.GameViewModel

class GameActivity : AppCompatActivity() {

    companion object {
        const val GAME_BEGIN_DIALOG_TAG = "game_dialog_tag"
        const val GAME_END_DIALOG_TAG = "game_end_dialog_tag"
        const val NO_WINNER = "No one"
    }

    private lateinit var gameViewModel : GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        promptForPlayers()
    }

    fun promptForPlayers() {
        val dialog = GameBeginDialog.newInstance(this)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, GAME_BEGIN_DIALOG_TAG)
    }

    fun onPlayersSet(player1: String, player2: String) {
        initDataBinding(player1, player2)
    }

    private fun initDataBinding(player1: String, player2: String) {
        val activityGameBinding = DataBindingUtil.setContentView<ActivityGameBinding>(this, R.layout.activity_game)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        gameViewModel.init(player1, player2)
        activityGameBinding.gameViewModel = gameViewModel
        setUpOnGameEndListener()
    }

    private fun setUpOnGameEndListener() {
        gameViewModel.getWinner().observe(this, Observer { player: Player? -> onGameWinnerChanged(player!!) })
    }

    fun quitGame() {
        finish()
        System.exit(0)
    }

    @VisibleForTesting
    fun onGameWinnerChanged(winner: Player) {
        val winnerName = if (winner.name.isEmpty()) NO_WINNER else winner.name
        val dialog: GameEndDialog = GameEndDialog.newInstance(this, winnerName)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, GAME_END_DIALOG_TAG)
    }

}
