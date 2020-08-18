package eu.remilapointe.country.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.remilapointe.country.R

class CountryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CountryFragment(applicationContext))
                .commitNow()
        }
    }
}
