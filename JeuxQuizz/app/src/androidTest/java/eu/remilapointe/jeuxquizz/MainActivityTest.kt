package eu.remilapointe.jeuxquizz

import android.content.res.Resources
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.assertion.ViewAssertions.matches

class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private var resources: Resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
    private lateinit var stringToBeChecked: String

    @Before
    fun getStringFromResources() {
        stringToBeChecked = resources.getString(R.string.app_name)
    }

    @Test
    fun checkIfSelQuizIsCorrect() {
        Espresso.onView(withId(R.id.tv_title)).check(matches(withText(stringToBeChecked)))
    }
}
