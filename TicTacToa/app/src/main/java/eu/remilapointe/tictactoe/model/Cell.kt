package eu.remilapointe.tictactoe.model

class Cell(val player: Player) {
    fun isEmpty() : Boolean {
        return player.value.isEmpty()
    }
}
