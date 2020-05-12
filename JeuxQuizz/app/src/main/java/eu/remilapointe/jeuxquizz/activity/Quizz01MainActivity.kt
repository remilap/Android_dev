package eu.remilapointe.jeuxquizz.activity

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.log4k.d
import eu.remilapointe.jeuxquizz.R
import eu.remilapointe.jeuxquizz.business.Question
import kotlinx.android.synthetic.main.activity_quizz01_quizz.*
import org.jetbrains.anko.toast
import java.util.*
import java.util.Collections.shuffle

class Quizz01MainActivity : AppCompatActivity() {

    private var colorStateList: ColorStateList? = null
    private var colorStateListCountDown: ColorStateList? = null
    private var countDownTimer: CountDownTimer? = null

    private var timeLeft: Long = 0

    private var questionSetsList = mutableListOf<Question>()

    private var qCounter: Int = 0
    private var currQuestion: Question? = null
    private var qCountTotal: Int = 0

    private var score: Int = 0
    private var ans: Boolean = false

    private var onBackPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz01_quizz)

        colorStateList = rb_rep1_01.textColors
        colorStateListCountDown = tv_time_counter.textColors

        tv_score.text = resources.getString(R.string.st_score, score)

        loadTest()

        qCountTotal = questionSetsList!!.size
        shuffle(questionSetsList)

        showQuestion()

        bt_submit.setOnClickListener {
            if (!ans) {
                if (rb_rep1_01.isChecked || rb_rep2_01.isChecked || rb_rep3_01.isChecked) {
                    check()
                } else {
                    toast("Select an answer first")
                }
            } else {
                showQuestion()
            }
        }

    }

    private fun showQuestion() {
        rb_rep1_01.setTextColor(colorStateList)
        rb_rep2_01.setTextColor(colorStateList)
        rb_rep3_01.setTextColor(colorStateList)
        rg_reponses_01.clearCheck()

        if (qCounter < qCountTotal) {
            currQuestion = questionSetsList?.get(qCounter) as Question?
            tv_question.text = currQuestion?.mQuestion
            rb_rep1_01.setText(currQuestion?.mOption1)
            rb_rep2_01.setText(currQuestion?.mOption2)
            rb_rep3_01.setText(currQuestion?.mOption3)

            qCounter++
            tv_question_count.text = resources.getString(R.string.st_question, qCounter, qCountTotal)
            ans = false
            bt_submit.setText(R.string.st_submit)
            timeLeft = COUNTDOWN_TIMER
            startCountDown()
        } else {
            finishQuizActivity()
        }
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateCountDown()
            }
            override fun onFinish() {
                timeLeft = 0
                updateCountDown()
                check()
            }
        }.start()
    }

    private fun updateCountDown() {
        val min = (timeLeft / 1000).toInt() / 60
        val sec = (timeLeft / 1000).toInt() % 60
        val timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        tv_time_counter.text = timeFormat
        if (timeLeft < 10000) {
            tv_time_counter.setTextColor(Color.RED)
        } else {
            tv_time_counter.setTextColor(colorStateList)
        }
    }

    private fun check() {
        ans = true
        countDownTimer?.cancel()
        val answerOptSelecte = findViewById<View>(rg_reponses_01.checkedRadioButtonId) as RadioButton
        val answer = rg_reponses_01.indexOfChild(answerOptSelecte) + 1
        if (answer == currQuestion?.mRightAns) {
            score++
            tv_score.text = resources.getString(R.string.st_score, score)
            tv_question.text = resources.getString(R.string.st_correct_answer)
        } else {
            tv_question.text = resources.getString(R.string.st_incorrect_answer)
        }
        showRightAnswer()
    }

    private fun showRightAnswer() {
        rb_rep1_01.setTextColor(Color.RED)
        rb_rep2_01.setTextColor(Color.RED)
        rb_rep3_01.setTextColor(Color.RED)
        when (currQuestion?.mRightAns) {
            1 -> rb_rep1_01.setTextColor(Color.GREEN)
            2 -> rb_rep2_01.setTextColor(Color.GREEN)
            3 -> rb_rep3_01.setTextColor(Color.GREEN)
        }

        if (qCounter < qCountTotal) {
            bt_submit.text = resources.getString(R.string.st_next)
        } else {
            bt_submit.text = resources.getString(R.string.st_finish)
        }
    }

    private fun finishQuizActivity() {
        val rIntent = Intent()
        rIntent.putExtra(FINAL_SCORE, score)
        setResult(Activity.RESULT_OK, rIntent)
        finish()
    }

    override fun onBackPressed() {
        if (onBackPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuizActivity()
        } else {
            toast("Press Back Again")
        }
        onBackPressedTime = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    private fun loadTest() {
        questionSetsList.clear()
        val q1 = Question("2+2", "1", "4", "2", 2)
        addQuestion(q1)
        val q2 = Question("What is Capital of India", "Mumbai", "New Delhi", "Chennai", 2)
        addQuestion(q2)
        val q3 = Question("The author of Harry Potter book is", "J.K Rowling", "Stephen King", "Toni Morrison", 1)
        addQuestion(q3)
        val q4 = Question("Which is fourth planet from sun?", "Mars", "Earth", "Venus", 1)
        addQuestion(q4)
        val q5 = Question("How many gram are there in a kilogram", "100", "1000", "10000", 2)
        addQuestion(q5)
    }

    private fun addQuestion(question: Question) {
        questionSetsList.add(question)
        d(question.toString() + " / nb elem: " + questionSetsList.size)
    }

    companion object {
        const val FINAL_SCORE = "FinalScore"
        private const val COUNTDOWN_TIMER: Long = 20000
    }
}
