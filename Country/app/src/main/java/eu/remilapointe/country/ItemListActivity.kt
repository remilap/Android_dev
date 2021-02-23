package eu.remilapointe.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.log4k.d
import eu.remilapointe.country.db.CountryDb

import eu.remilapointe.country.entity.Country
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

    var countryList: MutableList<Country> = arrayListOf()
    lateinit var rvCountries: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        d("OnCreate ItemListActivity")
        rvCountries = item_list

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            d("Snackbar action")
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val countryDao = CountryDb.getDatabase(applicationContext).countryDao()
        countryDao.getAll().value?.forEach { it -> countryList.add(it) }
        d("nb on initial countries: ${countryList.size}")

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        d("setupRecyclerView")
        val countryDao = CountryDb.getDatabase(applicationContext).countryDao()
        recyclerView.adapter =
            SimpleItemRecyclerViewAdapter(
                this,
                countryDao.getAll().value!!
            )
    }

}
