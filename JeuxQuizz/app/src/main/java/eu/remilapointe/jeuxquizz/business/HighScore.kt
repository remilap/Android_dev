package eu.remilapointe.jeuxquizz.business

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class HighScore(activity: Activity, private val whichPref: String) {

    var preferences: SharedPreferences
    var mhighscore: Int = 0

    init {
        preferences = activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun getUpdatedHighScore(score: Int) : Int {
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
        mhighscore = preferences.getInt(whichPref, 0)
        return mhighscore
    }

    companion object {
        val PREFS = "quizz_shared_prefs"
        val HIGH_SCORE_QUIZ01 = "high_score_quiz01"
        val HIGH_SCORE_DEPARTEMENT = "high_score_departement"
    }

}
