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
import com.remilapointe.laser.db.Colori

fun Context.ColoriDetailIntent(elem: String): Intent {
    return Intent(this, ColoriAddActivity::class.java).apply {
        d("produit.val= $elem")
        putExtra(EXTRA_QUERY_COLORI, elem)
    }
}

private const val EXTRA_QUERY_COLORI = "com.remilapointe.laser.QUERY_COLORI"

class ColoriAddActivity : AppCompatActivity() {

    private lateinit var editTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.colori_add_activity)
        editTxt = findViewById(R.id.edColori)
        d("onCreate begin")

        val button = findViewById<Button>(R.id.bt_save_colori)
        button.setOnClickListener {
            d("click on ${Colori.ELEM} save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTxt.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elem = editTxt.text.toString()
                d("${Colori.ELEM} to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_COLORI, elem)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            this.finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_COLORI = "com.remilapointe.laser.REPLY_COLORI"
    }

}
