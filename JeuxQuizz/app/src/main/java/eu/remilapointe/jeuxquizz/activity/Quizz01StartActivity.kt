package eu.remilapointe.jeuxquizz.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.remilapointe.jeuxquizz.R
import kotlinx.android.synthetic.main.activity_quizz01_start.*

class Quizz01StartActivity : AppCompatActivity() {

    private var mhighscore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz01_start)

        loadHighScore()
        bt_start.setOnClickListener {
            startActivityForResult(Intent(applicationContext, Quizz01MainActivity::class.java), REQUEST_CODE_01)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_01) {
            if (resultCode == Activity.RESULT_OK) {
                val score = data?.getIntExtra(Quizz01MainActivity.FINAL_SCORE, 0)
                if (score!! > mhighscore) {
                    updateScore(score)
                }
            }
        }
    }

    private fun updateScore(score: Int) {
        mhighscore = score
        tv_highscore!!.text = resources.getString(R.string.st_my_highscore, mhighscore)

        val preferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(HIGH_SCORE, mhighscore)
        editor.apply()
    }

    private fun loadHighScore() {
        val preferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        mhighscore = preferences.getInt(HIGH_SCORE, 0)
        tv_highscore!!.text = resources.getString(R.string.st_my_highscore, mhighscore)
    }

    companion object {
        const val REQUEST_CODE_01 = 1
        val PREFS = "quizz_shared_prefs"
        val HIGH_SCORE = "high_score"
    }

}
