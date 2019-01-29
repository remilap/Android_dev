package eu.remilapointe.tictactoe

import eu.remilapointe.tictactoe.model.Cell
import eu.remilapointe.tictactoe.model.Game
import eu.remilapointe.tictactoe.model.Player
import org.junit.Before
import org.junit.Test

class GameDiagonalCellsShould {

    lateinit var game: Game
    lateinit var player: Player
    lateinit var anotherPlayer: Player

    @Before
    fun setUp() {
        game = Game("aaa", "bbb")
        player = game.player1
        anotherPlayer = game.player2
    }

    @Test
    fun returnTrueIfHasThreeSameDiagonalCellsTopLeft() {
        val cell = Cell(player)
        for (r in 0  until Game.BOARD_SIZE)
            game.setCell(r, r, cell)
        val res = game.hasThreeSameDiagonalCells()
        assert(res)
    }

    @Test
    fun returnTrueIfHasThreeSameDiagonalCellsTopRight() {
        val cell = Cell(player)
        for (r in 0  until Game.BOARD_SIZE)
            game.setCell(r, Game.BOARD_SIZE - r, cell)
        val res = game.hasThreeSameDiagonalCells()
        assert(res)
    }

    @Test
    fun returnFalseIfHasNotThreeSameDiagonalCellsTopLeft() {
        val cell = Cell(player)
        val anotherCell = Cell(anotherPlayer)
        for (r in 0  until Game.BOARD_SIZE)
            game.setCell(r, r, cell)
        game.setCell(0, 0, anotherCell)
        val res = game.hasThreeSameDiagonalCells()
        assert(! res)
    }

}
