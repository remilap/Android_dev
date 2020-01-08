package com.remilapointe.laser.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.log4k.d
import com.remilapointe.laser.R
import org.jetbrains.anko.toast

fun Context.TailleDetailIntent(elem: String): Intent {
    return Intent(this, TailleAddActivity::class.java).apply {
        putExtra(EXTRA_QUERY_TAILLE, elem)
    }
}

private const val EXTRA_QUERY_TAILLE = "com.remilapointe.laser.QUERY_TAILLE"

class TailleAddActivity : AppCompatActivity() {

    private lateinit var editTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.taille_add_activity)
        editTxt = findViewById(R.id.edit_taille)
        d("onCreate begin")

        val button = findViewById<Button>(R.id.bt_save_taille)
        button.setOnClickListener {
            d("click on taille save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTxt.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elem = editTxt.text.toString()
                d("taille to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_TAILLE, elem)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            this.finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_TAILLE = "com.remilapointe.laser.REPLY_TAILLE"
    }

}
