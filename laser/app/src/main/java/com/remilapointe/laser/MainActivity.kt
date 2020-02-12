package com.remilapointe.laser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.remilapointe.laser.ui.main.achatDetailIntent
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        bt_achat.setOnClickListener{
            startActivity(achatDetailIntent())
        }

        bt_commande.setOnClickListener{
            startActivity(Intent(this, ConfigurationActivity::class.java))
        }

        bt_configuration.setOnClickListener{
            startActivity(Intent(this, ConfigurationActivity::class.java))
        }

    }
}
