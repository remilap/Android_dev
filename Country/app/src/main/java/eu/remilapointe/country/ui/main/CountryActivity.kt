package eu.remilapointe.country.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.remilapointe.country.R
import eu.remilapointe.country.databinding.CountryActivityBinding

private lateinit var binding: CountryActivityBinding

class CountryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CountryActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
