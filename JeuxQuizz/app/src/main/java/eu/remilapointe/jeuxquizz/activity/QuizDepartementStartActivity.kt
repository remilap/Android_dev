package eu.remilapointe.jeuxquizz.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.remilapointe.jeuxquizz.R
import eu.remilapointe.jeuxquizz.business.HighScore
import kotlinx.android.synthetic.main.activity_departement_start.*
import kotlinx.android.synthetic.main.activity_quizz01_start.*

class QuizDepartementStartActivity : AppCompatActivity() {

    lateinit var highScoreMgt: HighScore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departement_start)

        highScoreMgt = HighScore(this, HighScore.HIGH_SCORE_QUIZ01)
        val mhighscore = highScoreMgt.getHighScore()
        displayHighScore(mhighscore)
        bt_start_dept.setOnClickListener {
            val intent = Intent(applicationContext, QuizDepartementMainActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_DEPT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_DEPT) {
            if (resultCode == Activity.RESULT_OK) {
                val score = data?.getIntExtra(Quiz01MainActivity.FINAL_SCORE, 0)
                val mhighscore = highScoreMgt.getUpdatedHighScore(score!!)
                displayHighScore(mhighscore)
            }
        }
    }

    private fun displayHighScore(score: Int) {
        tv_highscore_dept!!.text = resources.getString(R.string.st_my_highscore, score)
    }

    companion object {
        const val REQUEST_CODE_DEPT = 1
    }

}
