package com.remilapointe.cluedo.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.remilapointe.cluedo.R
import com.remilapointe.cluedo.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class PlayAgainstAI: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.log("")
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        bt_new_alone.setOnClickListener(this)
        bt_new_against_AI.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        Util.log("with p0=${p0?.id}")
    }
}
