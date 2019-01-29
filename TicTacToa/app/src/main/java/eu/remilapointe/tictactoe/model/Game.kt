package eu.remilapointe.tictactoe.model

import android.arch.lifecycle.MutableLiveData
import android.util.Log

class Game(playerOne: String, playerTwo: String) {

    companion object {
        val TAG: String = Game::class.simpleName!!
        const val BOARD_SIZE: Int = 3
        val emptyPlayer = Player("", "")
        val emptyCell = Cell(emptyPlayer)
    }

    var player1: Player = Player(playerOne, "X")
    var player2: Player = Player(playerTwo, "O")
    var currentPlayer: Player = player1
    private var cells = Array(BOARD_SIZE * BOARD_SIZE) { emptyCell }
    var winner : MutableLiveData<Player> = MutableLiveData()

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == player1) player2 else player1
    }

    fun getCell(row: Int, col: Int) = cells[BOARD_SIZE * row + col]

    fun setCell(row: Int, col: Int, cell: Cell) {
        cells[BOARD_SIZE * row + col] = cell
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

    fun hasThreeSameHorizontalCells() : Boolean {
        for (i in 0 until BOARD_SIZE)
            if (areEquals(arrayOf(getCell(i, 0), getCell(i, 1), getCell(i, 2)) )) {
                Log.d(Game.TAG, "Row ${i+1} with $BOARD_SIZE same cells: ${getCell(i, 0)}")
                return true
            }
        return false
    }

    fun hasThreeSameVerticalCells() : Boolean {
        for (i in 0 until BOARD_SIZE)
            if (areEquals(arrayOf(getCell(0, i), getCell(1, i), getCell(2, i)))) {
                Log.d(Game.TAG, "Column ${i+1} with $BOARD_SIZE same cells: ${getCell(0, i)}")
                return true
            }
        return false
    }

    fun hasThreeSameDiagonalCells() : Boolean {
        if (areEquals(arrayOf(getCell(0, 0), getCell(1, 1), getCell(2, 2)))) {
            Log.d(Game.TAG, "Diagonal NO-SE with $BOARD_SIZE same cells: ${getCell(0, 0)}")
            return true
        }
        if (areEquals(arrayOf(getCell(0, 2), getCell(1, 1), getCell(2, 0)))) {
            Log.d(Game.TAG, "Diagonal NE-SO with $BOARD_SIZE same cells: ${getCell(0, 2)}")
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

    fun isBoardFull() : Boolean {
        for (cell in cells)
            if (cell.isEmpty()) return false
        Log.d(Game.TAG, "Board is full")
        return true
    }

    fun reset() {
        player1 = Player("", "")
        player2 = Player("", "")
        currentPlayer = Player("", "")
        for (r in 0 until BOARD_SIZE)
            for (c in 0 until BOARD_SIZE)
                setCell(r, c, emptyCell)
    }

}
