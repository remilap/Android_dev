package eu.remilapointe.tictactoe

import eu.remilapointe.tictactoe.model.Cell
import eu.remilapointe.tictactoe.model.Game
import eu.remilapointe.tictactoe.model.Player
import org.junit.Before
import org.junit.Test

class GameVerticalCellsShould {

    lateinit var game: Game
    lateinit var player: Player
    lateinit var anotherPlayer: Player

    @Before
    fun setUp() {
        game = Game("aaa", "bbb")
        player = game.player1
        anotherPlayer = game.player2
    }

    private fun returnTrueIfHasThreeSameVerticalCellsAtColumn(c: Int) : Boolean {
        val cell = Cell(player)
        for (r in 0 until Game.BOARD_SIZE)
            game.setCell(r, c, cell)
        return game.hasThreeSameVerticalCells()
    }

    @Test
    fun returnTrueIfHasThreeSameVerticalCellsAtColumn1() {
        val res = returnTrueIfHasThreeSameVerticalCellsAtColumn(0)
        assert(res)
    }

    @Test
    fun returnTrueIfHasThreeSameVerticalCellsAtColumn2() {
        val res = returnTrueIfHasThreeSameVerticalCellsAtColumn(1)
        assert(res)
    }

    @Test
    fun returnTrueIfHasThreeSameVerticalCellsAtColumn3() {
        val res = returnTrueIfHasThreeSameVerticalCellsAtColumn(2)
        assert(res)
    }

    @Test
    fun returnFalseIfDoesNotHaveThreeSameVerticalCells() {
        returnTrueIfHasThreeSameVerticalCellsAtColumn(0)
        val anotherCell = Cell(anotherPlayer)
        game.setCell(2, 0, anotherCell)
        val hasThreeSameVerticalCells = game.hasThreeSameVerticalCells()
        assert(! hasThreeSameVerticalCells)
    }

}
