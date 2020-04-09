package com.remilapointe.laser

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val splashTimeOut: Long = 3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed(
            {
                // This method will be executed once the time is over
                // Start your app main activity
                startActivity(Intent(this, MainActivity::class.java))

                // close this activity
                finish()
            },
            splashTimeOut
        )
    }

}
