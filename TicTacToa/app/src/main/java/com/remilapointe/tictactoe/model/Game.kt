package com.remilapointe.tictactoe.model

import android.arch.lifecycle.MutableLiveData
import android.util.Log

class Game(playerOne: String, playerTwo: String) {

    companion object {
        val TAG: String = Game::class.simpleName!!
        const val BOARD_SIZE: Int = 3
        val emptyPlayer = Player("", "")
        val emptyCell = Cell(emptyPlayer)
    }

    private var player1: Player = Player(playerOne, "X")
    private var player2: Player = Player(playerTwo, "O")
    var currentPlayer: Player = player1
    var cells = Array(BOARD_SIZE * BOARD_SIZE) { emptyCell }
    var winner : MutableLiveData<Player> = MutableLiveData()

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == player1) player2 else player1
    }

    fun hasGameEnded() : Boolean {
        if (hasThreeSameHorizontalCells() || hasThreeSameVerticalCells() || hasThreeSameDiagonalCells()) {
            winner.value = currentPlayer
            return true
        }
        if (isBoardFull()) {
            winner.value = emptyPlayer
            return true
        }
        return false
    }

    private fun hasThreeSameHorizontalCells() : Boolean {
        for (i in 0 until BOARD_SIZE)
            if (areEquals(arrayOf(cells[BOARD_SIZE*i+0], cells[BOARD_SIZE*i+1], cells[BOARD_SIZE*i+2]) )) {
                Log.d(Game.TAG, "Row ${i+1} with $BOARD_SIZE same cells: ${cells[BOARD_SIZE*i]}")
                return true
            }
        return false
    }

    private fun hasThreeSameVerticalCells() : Boolean {
        for (i in 0 until BOARD_SIZE)
            if (areEquals(arrayOf(cells[0+i], cells[BOARD_SIZE+i], cells[BOARD_SIZE*2+i]))) {
                Log.d(Game.TAG, "Column ${i+1} with $BOARD_SIZE same cells: ${cells[i]}")
                return true
            }
        return false
    }

    private fun hasThreeSameDiagonalCells() : Boolean {
        if (areEquals(arrayOf(cells[0+0], cells[BOARD_SIZE+1], cells[BOARD_SIZE*2+2]))) {
            Log.d(Game.TAG, "Diagonal NO-SE with $BOARD_SIZE same cells: ${cells[0]}")
            return true
        }
        if (areEquals(arrayOf(cells[0+2], cells[BOARD_SIZE+1], cells[BOARD_SIZE*2+0]))) {
            Log.d(Game.TAG, "Diagonal NE-SO with $BOARD_SIZE same cells: ${cells[2]}")
            return true
        }
        return false
    }

    /**
     * 2 cells are equal if:
     * - Both are none null
     * - Both have non null values
     * - both have equal values
     *
     * @param cells: Cells to check if are equal
     * @return
     */
    private fun  areEquals(cells: Array<Cell>) : Boolean {
        if (cells.isEmpty()) return false
        for (cell in cells) {
            if (cell.player.value.isEmpty()) return false
        }
        val compBase: Cell = cells[0]
        for (i in 1 until cells.size)
            if (compBase.player.value != cells[i].player.value) return false

        return true
    }

    private fun isBoardFull() : Boolean {
        for (cell in cells)
            if (cell.isEmpty()) return false
        Log.d(Game.TAG, "Board is full")
        return true
    }

    fun reset() {
        player1 = Player("", "")
        player2 = Player("", "")
        currentPlayer = Player("", "")
        for (i in 0 until BOARD_SIZE* BOARD_SIZE)
            cells[i] = emptyCell
    }

}
