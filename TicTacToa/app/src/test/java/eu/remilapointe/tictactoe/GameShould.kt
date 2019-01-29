package eu.remilapointe.tictactoe

import android.arch.core.executor.testing.InstantTaskExecutorRule
import eu.remilapointe.tictactoe.model.Cell
import eu.remilapointe.tictactoe.model.Game
import eu.remilapointe.tictactoe.model.Player
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameShould {

    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var game: Game
    private lateinit var player: Player
    private lateinit var anotherPlayer: Player

    @Before
    fun setUp() {
        game = Game("aaa", "bbb")
        player = game.player1
        anotherPlayer = game.player2
    }

    @Test
    fun endGameIfHasThreeSameHorizontalCells() {
        val cell = Cell(player)
        for (c in 0 until Game.BOARD_SIZE)
            game.setCell(0, c, cell)
        val res = game.hasGameEnded()
        assert(res)
    }

    @Test
    fun endGameIfHasThreeSameVerticalCells() {
        val cell = Cell(player)
        for (r in 0 until Game.BOARD_SIZE)
            game.setCell(r, 0, cell)
        val res = game.hasGameEnded()
        assert(res)
    }

    @Test
    fun endGameIfHasThreeSameDiagonalCells() {
        val cell = Cell(player)
        for (r in 0 until Game.BOARD_SIZE)
            game.setCell(r, r, cell)
        val res = game.hasGameEnded()
        assert(res)
    }

    @Test
    fun endGameIfBoardIsFull() {
        val cell = Cell(player)
        val anotherCell = Cell(anotherPlayer)
        game.setCell(0, 0, cell)
        game.setCell(0, 1, anotherCell)
        game.setCell(0, 2, cell)
        game.setCell(1, 0, anotherCell)
        game.setCell(1, 1, cell)
        game.setCell(1, 2, anotherCell)
        game.setCell(2, 0, cell)
        game.setCell(2, 1, anotherCell)
        game.setCell(2, 2, cell)
        val res = game.isBoardFull()
        assert(res)
    }

    @Test
    fun switchFromPlayer1ToPlayer2() {
        game.currentPlayer = game.player1
        game.switchPlayer()
        val res = game.currentPlayer == game.player2
        assert(res)
    }

}
