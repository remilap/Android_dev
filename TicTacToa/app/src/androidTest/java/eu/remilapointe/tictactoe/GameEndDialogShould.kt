package eu.remilapointe.tictactoe

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.schibsted.spain.barista.BaristaAssertions.assertDisplayed
import com.schibsted.spain.barista.BaristaAssertions.assertNotExist
import com.schibsted.spain.barista.BaristaDialogActions.clickDialogPositiveButton
import eu.remilapointe.tictactoe.model.Player
import eu.remilapointe.tictactoe.view.GameActivity
import org.junit.Rule
import org.junit.Test

class GameEndDialogShould {

    @Rule
    val activityRule = ActivityTestRule<GameActivity>(GameActivity::class.java, true, false)

    private val context = InstrumentationRegistry.getTargetContext()

    @Test
    fun display_winner_when_game_ends() {
        givenGameActivityLaunched()
        givenGameEnded()
        assertDisplayed(R.id.tv_winner)
    }

    @Test
    fun display_begin_dialog_when_done_clicked() {
        givenGameActivityLaunched()
        givenGameEnded()
        clickDialogPositiveButton()
        assertNotExist(R.id.tv_winner)
        assertDisplayed(R.id.et_player1)
    }

    private fun givenGameActivityLaunched() {
        val intent = Intent(context, GameActivity::class.java)
        activityRule.launchActivity(intent)
    }

    private fun givenGameEnded() {
        val gameActivity = activityRule.activity
        gameActivity.onGameWinnerChanged(Player("aaa", "X"))
    }

}
