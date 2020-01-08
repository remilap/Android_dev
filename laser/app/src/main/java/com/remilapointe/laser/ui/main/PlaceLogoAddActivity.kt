package com.remilapointe.laser.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.log4k.d
import com.remilapointe.laser.R
import org.jetbrains.anko.toast

fun Context.PlaceLogoDetailIntent(elem: String): Intent {
    return Intent(this, PlaceLogoAddActivity::class.java).apply {
        putExtra(EXTRA_QUERY_PLACELOGO, elem)
    }
}

private const val EXTRA_QUERY_PLACELOGO = "com.remilapointe.laser.QUERY_PLACELOGO"

class PlaceLogoAddActivity : AppCompatActivity() {

    private lateinit var editTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.placelogo_add_activity)
        editTxt = findViewById(R.id.edit_placelogo)
        d("onCreate begin")

        val button = findViewById<Button>(R.id.bt_save_placelogo)
        button.setOnClickListener {
            d("click on placelogo save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTxt.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elem = editTxt.text.toString()
                d("placelogo to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_PLACELOGO, elem)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            this.finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_PLACELOGO = "com.remilapointe.laser.REPLY_PLACELOGO"
    }

}
