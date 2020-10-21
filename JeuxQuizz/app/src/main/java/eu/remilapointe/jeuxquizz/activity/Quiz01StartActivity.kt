package eu.remilapointe.jeuxquizz.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.emptyPreferences
import androidx.lifecycle.lifecycleScope
import eu.remilapointe.jeuxquizz.MainActivity
import eu.remilapointe.jeuxquizz.R
import eu.remilapointe.jeuxquizz.SettingsManager
import eu.remilapointe.jeuxquizz.SettingsManager.Companion.QUIZ_REFERENCE
import eu.remilapointe.jeuxquizz.SettingsManager.Companion.quizList
import eu.remilapointe.jeuxquizz.business.HighScore
import kotlinx.android.synthetic.main.activity_quizz01_start.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class Quiz01StartActivity : AppCompatActivity() {

    private lateinit var settingsManager: SettingsManager
    private lateinit var highScoreMgt: HighScore
    private var selectedQuizId = 0
    private var highScores = emptyArray<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz01_start)

        selectedQuizId = savedInstanceState?.get(QUIZ_REFERENCE) as Int

        settingsManager = SettingsManager(applicationContext)
        lifecycleScope.launch {
            settingsManager.selQuizPref.collectLatest {
                selectedQuizId = it
            }
            settingsManager.highScore.collectLatest {
                highScores = it
            }
        }

        highScoreMgt = HighScore(this, selectedQuizId)
        val mhighscore = highScoreMgt.getHighScore()
        displayHighScore(mhighscore)

        bt_start_quiz01.setOnClickListener {
            val intent = Intent(applicationContext, Quiz01MainActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_01)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_01) {
            if (resultCode == Activity.RESULT_OK) {
                val score = data?.getIntExtra(Quiz01MainActivity.FINAL_SCORE, 0)
                val mhighscore = highScoreMgt.getUpdatedHighScore(score!!)
                displayHighScore(mhighscore)
            }
        }
    }

    private fun displayHighScore(score: Int) {
        tv_highscore_quiz01!!.text = resources.getString(R.string.st_my_highscore, score)
    }

    companion object {
        const val REQUEST_CODE_01 = 1
    }

}
