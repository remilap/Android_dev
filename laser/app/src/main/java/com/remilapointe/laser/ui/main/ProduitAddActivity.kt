package com.remilapointe.laser.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.*
import kotlinx.android.synthetic.main.produit_add_activity.*

fun Context.ProduitDetailIntent(elem: String): Intent {
    return Intent(this, ProduitAddActivity::class.java).apply {
        d("produit.val= $elem")
        putExtra(EXTRA_QUERY_PRODUIT, elem)
    }
}

private const val EXTRA_QUERY_PRODUIT = "com.remilapointe.laser.QUERY_PRODUIT"

class ProduitAddActivity : AppCompatActivity() {

    private lateinit var editTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produit_add_activity)
        editTxt = findViewById(R.id.edProduit)
        d("onCreate begin")

        val button = findViewById<Button>(R.id.bt_save_produit)
        button.setOnClickListener {
            d("click on ${Produit.ELEM} save button")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTxt.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val elem = editTxt.text.toString()
                d("${Produit.ELEM} to save: $elem")
                replyIntent.putExtra(EXTRA_REPLY_PRODUIT, elem)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            this.finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_PRODUIT = "com.remilapointe.laser.REPLY_PRODUIT"
    }

}
