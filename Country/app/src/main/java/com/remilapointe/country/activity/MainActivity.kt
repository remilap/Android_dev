package com.remilapointe.country.activity

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.country.R
import com.remilapointe.country.adapter.CountryAdapter
import com.remilapointe.country.db.CountryDb
import com.remilapointe.country.entity.Country
import com.remilapointe.country.model.CountryViewHolder
import com.remilapointe.country.model.CountryViewModel
import com.remilapointe.country.tools.DbWorkerThread
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.Serializable
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(CountryViewModel::class.java)
    }

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        // Create adapter for the RecyclerView
        val adapter = CountryAdapter { country: Country -> countryClicked(country) }
        countryList.adapter = adapter

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        viewModel.allCountries.observe(this, Observer(adapter::submitList))

        initAddButtonListener()
        initSwipeToDelete()
    }

    private fun copyDb() {
        val backupDBPath = CountryDb.getInstance(applicationContext).openHelper.writableDatabase
        val fDb = File(backupDBPath.path)
        longToast("database file: ${fDb.absolutePath}")
        val sd = Environment.getExternalStorageDirectory()
        if (fDb.exists()) {
            val src = FileInputStream(fDb).channel
            val bckDb = File(sd, fDb.name)
            try {
                sd.mkdirs()
                longToast("backup db: ${bckDb.absolutePath}")
                val dst = FileOutputStream(bckDb).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            } catch (e: Exception) {
                toast("unable to create external file")
            }
        }

    }

    private fun countryClicked(country: Country) {
        Toast.makeText(this, "Clicked: ${country.name_fr}", Toast.LENGTH_LONG).show()

        // Launch second activity, pass album ...
        val showDetailActivityIntent = Intent(this, CountryDetailsActivity::class.java)
        showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, country as Serializable)
        startActivity(showDetailActivityIntent)
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CountryViewHolder).country?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(countryList)
    }

    private fun addCountry() {
        val newCountry = inputText.text.trim()
        if (newCountry.isNotEmpty()) {
            viewModel.insert(newCountry)
            inputText.setText("")
        }
    }

    private fun initAddButtonListener() {
        addButton.setOnClickListener {
            addCountry()
        }

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCountry()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }
        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCountry()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }
    }

}
