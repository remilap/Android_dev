package eu.remilapointe.tictactoe

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.schibsted.spain.barista.BaristaAssertions.assertDisplayed
import com.schibsted.spain.barista.BaristaAssertions.assertNotExist
import com.schibsted.spain.barista.BaristaDialogActions.clickDialogPositiveButton
import com.schibsted.spain.barista.BaristaEditTextActions.writeToEditText
import eu.remilapointe.tictactoe.view.GameActivity
import org.junit.Rule
import org.junit.Test

class GameBeginDialogShould {

    @Rule
    val activityRule = ActivityTestRule<GameActivity>(GameActivity::class.java, true, false)

    private val context = InstrumentationRegistry.getTargetContext()

    @Test
    fun display_empty_names_message_if_names_empty() {
        val intent = Intent(context, GameActivity::class.java)
        activityRule.launchActivity(intent)
        writeToEditText(R.id.et_player1, "")
        writeToEditText(R.id.et_player2, "")
        clickDialogPositiveButton()
        assertDisplayed(R.string.game_dialog_empty_name)
    }

    @Test
    fun display_same_names_message_if_names_same() {
        val intent = Intent(context, GameActivity::class.java)
        activityRule.launchActivity(intent)
        writeToEditText(R.id.et_player1, "aaa")
        writeToEditText(R.id.et_player2, "aaa")
        clickDialogPositiveButton()
        assertDisplayed(R.string.game_dialog_same_names)
    }

    @Test
    fun display_empty_name_message_if_one_name_empty() {
        val intent = Intent(context, GameActivity::class.java)
        activityRule.launchActivity(intent)
        writeToEditText(R.id.et_player1, "")
        writeToEditText(R.id.et_player2, "bbb")
        clickDialogPositiveButton()
        assertDisplayed(R.string.game_dialog_empty_name)
    }

    @Test
    fun close_dialog_after_names_valid() {
        val intent = Intent(context, GameActivity::class.java)
        activityRule.launchActivity(intent)
        writeToEditText(R.id.et_player1, "aaa")
        writeToEditText(R.id.et_player2, "bbb")
        clickDialogPositiveButton()
        assertNotExist(R.id.layout_player1)
    }

}