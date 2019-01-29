package eu.remilapointe.tictactoe

import eu.remilapointe.tictactoe.model.Cell
import eu.remilapointe.tictactoe.model.Game
import eu.remilapointe.tictactoe.model.Player
import org.junit.Before
import org.junit.Test

class GameHorizontalCellsShould {

    lateinit var game: Game
    lateinit var player: Player
    lateinit var anotherPlayer: Player

    @Before
    fun setUp() {
        game = Game("aaa", "bbb")
        player = game.player1
        anotherPlayer = game.player2
    }

    private fun returnTrueIfHasThreeSameHorizontalCellsAtRow(r: Int) : Boolean {
        val cell = Cell(player)
        for (c in 0 until Game.BOARD_SIZE)
            game.setCell(r, c, cell)
        return game.hasThreeSameHorizontalCells()
    }

    @Test
    fun returnTrueIfHasThreeSameHorizontalCellsAtRow1() {
        val res = returnTrueIfHasThreeSameHorizontalCellsAtRow(0)
        assert(res)
    }

    @Test
    fun returnTrueIfHasThreeSameHorizontalCellsAtRow2() {
        val res = returnTrueIfHasThreeSameHorizontalCellsAtRow(1)
        assert(res)
    }

    @Test
    fun returnTrueIfHasThreeSameHorizontalCellsAtRow3() {
        val res = returnTrueIfHasThreeSameHorizontalCellsAtRow(2)
        assert(res)
    }

    @Test
    fun returnFalseIfDoesNotHaveThreeSameHorizontalCells() {
        returnTrueIfHasThreeSameHorizontalCellsAtRow(0)
        val anotherCell = Cell(anotherPlayer)
        game.setCell(0, 2, anotherCell)
        val hasThreeSameHorizontalCells = game.hasThreeSameHorizontalCells()
        assert(! hasThreeSameHorizontalCells)
    }

}
