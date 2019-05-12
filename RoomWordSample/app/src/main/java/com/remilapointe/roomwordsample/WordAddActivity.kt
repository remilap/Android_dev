package com.remilapointe.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

/**
 * Activity for entering a word.
 */
class WordAddActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var editTradView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
        editTradView = findViewById(R.id.edit_trad)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_WORD, word)
                val trad = editTradView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_TRAD, trad)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_WORD = "com.remilapointe.roomwordsample.REPLY_WORD"
        const val EXTRA_REPLY_TRAD = "com.remilapointe.roomwordsample.REPLY_TRAD"
    }

}
