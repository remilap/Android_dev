package eu.remilapointe.tictactoe.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayMap
import android.util.Log
import eu.remilapointe.tictactoe.model.Cell
import eu.remilapointe.tictactoe.model.Game
import eu.remilapointe.tictactoe.model.Player

class GameViewModel : ViewModel() {
    lateinit var cells : ObservableArrayMap<String, String>
    private lateinit var game : Game

    fun init(player1: String, player2: String) {
        game = Game(player1, player2)
        cells = ObservableArrayMap()
    }

    fun onClickedCellAt(row: Int, column: Int) {
        Log.d(Game.TAG, "onClickedCellAt($row, $column)")
        if (game.cells[Game.BOARD_SIZE*row+column].player.value.isEmpty()) {
            game.cells[Game.BOARD_SIZE*row+column] = Cell(game.currentPlayer)
            cells[stringFromNumbers(row, column)] = game.currentPlayer.value

            if (game.hasGameEnded())
                game.reset()
            else
                game.switchPlayer()
        }
    }

    private fun stringFromNumbers(row: Int, column: Int): String? {
        val res = row.toString() + column.toString()
        Log.d(Game.TAG, "stringFromNumbers($row, $column) returns $res")
        return res
    }

    fun getWinner() : LiveData<Player> {
        Log.d(Game.TAG, "getWinner return ${game.winner.value}")
        return game.winner
    }

}
