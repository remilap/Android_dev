package eu.remilapointe.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.log4k.d
import eu.remilapointe.country.dao.CountryDao
import eu.remilapointe.country.db.CountryDb

import eu.remilapointe.country.dummy.DummyContent
import eu.remilapointe.country.entity.Country
import eu.remilapointe.country.repository.CountryRepo
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    var countryLis: ArrayList<Country> = arrayListOf()
    lateinit var rvCountries: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        d("OnCreate ItemListActivity")
        rvCountries = findViewById(R.id.item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            d("Snackbar action")
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val countryDao = CountryDb.getDatabase(applicationContext).countryDao()
        countryDao.getAll().value?.forEach { it -> DummyContent.addItem(it) }
        d("nb on initial countries: ${DummyContent.ITEMS.size}")

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        d("setupRecyclerView, twoPane=$twoPane")
        val countryDao = CountryDb.getDatabase(applicationContext).countryDao()
        recyclerView.adapter =
            SimpleItemRecyclerViewAdapter(
                this,
                countryDao.getAll().value!!,
                twoPane
            )
    }

}
