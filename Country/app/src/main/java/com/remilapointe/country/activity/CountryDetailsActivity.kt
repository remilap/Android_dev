package com.remilapointe.country.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.remilapointe.country.R
import com.remilapointe.country.db.CountryDb
import com.remilapointe.country.entity.Country
import kotlinx.android.synthetic.main.country_details.*
import org.jetbrains.anko.toast

class CountryDetailsActivity : AppCompatActivity(), OnEditorActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_details)

        val dao = CountryDb.getInstance(this.applicationContext).countryDao()
        val intentThatStartedThisActivity = intent

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            val country = intentThatStartedThisActivity.extras!!.get(Intent.EXTRA_TEXT) as Country
            var modif = false
            txt_name.setText(country.name_fr)
            txt_name.setOnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    country.name_fr = txt_name.text.toString().trim()
                    toast("modif country name_fr: "+country.name_fr)
                    modif = true
                    bt_ok.isEnabled = true
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            txt_capital.setText(country.capitale_fr)
            txt_capital.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    country.capitale_fr = txt_capital.text.toString().trim()
                    toast("modif country capital_fr: "+country.capitale_fr)
                    modif = true
                    bt_ok.isEnabled = true
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            txt_surface.setText(String.format("%,d", country.surface))
            txt_surface.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    country.surface = txt_surface.text.subSequence(0, txt_surface.text.length-4).toString().replace("\\s+", "").toLong()
                    modif = true
                    bt_ok.isEnabled = true
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            txt_population.setText(String.format("%,d", country.population))
            txt_population.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    country.population = txt_population.text.toString().replace("\\s+", "").toLong()
                    modif = true
                    bt_ok.isEnabled = true
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            if (modif) {
                toast("update country in db")
                dao.updateCountry(country)
            }
            applicationContext.toast("details for: "+txt_name.text+", "+txt_capital.text+", "+txt_surface+", "+txt_population)
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId != EditorInfo.IME_ACTION_DONE) {
            return false
        }
        when (v?.id) {
            txt_name.id -> {

            }
        }
        return true
    }

}
