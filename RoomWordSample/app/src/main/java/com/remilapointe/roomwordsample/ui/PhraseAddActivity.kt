package com.remilapointe.roomwordsample.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.remilapointe.roomwordsample.R

class PhraseAddActivity : AppCompatActivity() {

    private lateinit var editPhraseView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phrase_add_activity)
        editPhraseView = findViewById(R.id.edit_phrase)

        val button = findViewById<Button>(R.id.bt_save_phrase)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editPhraseView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val phrase = editPhraseView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_PHRASE, phrase)
                setResult(Activity.RESULT_OK, replyIntent)
            }
        }
    }

    companion object {
        const val EXTRA_REPLY_PHRASE = "com.remilapointe.roomwordsample.REPLY_PHRASE"
    }

}
