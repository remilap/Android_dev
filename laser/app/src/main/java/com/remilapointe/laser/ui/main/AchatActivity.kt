package com.remilapointe.laser.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.remilapointe.laser.R

fun Context.achatDetailIntent(

): Intent {
    return Intent(this, AchatActivity::class.java).apply {

    }
}

class AchatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.achat_activity)

    }

}
