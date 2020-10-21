package eu.remilapointe.jeuxquizz.business

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import eu.remilapointe.jeuxquizz.MainActivity
import eu.remilapointe.jeuxquizz.SettingsManager

class HighScore(activity: Activity, private val whichPrefInt: Int) {

    var preferences: SharedPreferences
    var mhighscore: Int = 0

    init {
        preferences = activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun getUpdatedHighScore(score: Int) : Int {
        val whichPref = SettingsManager.quizList[whichPrefInt]
        mhighscore = preferences.getInt(whichPref, 0)
        if (score > mhighscore) {
            mhighscore = score
            val editor = preferences.edit()
            editor.putInt(whichPref, score)
            editor.apply()
        }
        return mhighscore
    }

    fun getHighScore() : Int {
        val whichPref = SettingsManager.quizList[whichPrefInt]
        mhighscore = preferences.getInt(whichPref, 0)
        return mhighscore
    }

    companion object {
        const val PREFS = "quizz_shared_prefs"
        const val HIGH_SCORE_QUIZ01 = "high_score_quiz01"
        val HIGH_SCORE_DEPARTEMENT = "high_score_departement"
    }

}
