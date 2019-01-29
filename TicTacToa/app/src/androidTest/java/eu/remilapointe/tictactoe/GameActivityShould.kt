package eu.remilapointe.tictactoe

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.schibsted.spain.barista.BaristaAssertions.assertDisplayed
import com.schibsted.spain.barista.BaristaClickActions.click
import eu.remilapointe.tictactoe.model.Player
import eu.remilapointe.tictactoe.view.GameActivity
import org.junit.Rule
import org.junit.Test

class GameActivityShould {

    @Rule
    val activityRule = ActivityTestRule<GameActivity>(GameActivity::class.java, true, false)

    private val context = InstrumentationRegistry.getTargetContext()

    private val player1 = Player("aaa", "X")
    private val player2 = Player("bbb", "O")

    @Test
    fun end_game_when_one_player_wins() {
        givenGameActivityLaunched()
        givenPlayersSet()
        click(R.id.cell_00)
        click(R.id.cell_10)
        click(R.id.cell_01)
        click(R.id.cell_11)
        click(R.id.cell_02)
        assertDisplayed(R.id.tv_winner)
        assertDisplayed(player1.name)
    }

    private fun givenGameActivityLaunched() {
        val intent = Intent(context, GameActivity::class.java)
        activityRule.launchActivity(intent)
    }

    private fun givenPlayersSet() {
        val gameActivity = activityRule.activity
        gameActivity.onPlayersSet(player1.name, player2.name)
    }

}
