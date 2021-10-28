package eu.remilapointe.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import eu.remilapointe.country.databinding.ActivityItemListBinding
import eu.remilapointe.country.db.CountryDb
import eu.remilapointe.country.entity.Country
import mu.KotlinLogging

private lateinit var binding: ActivityItemListBinding
private val logger = KotlinLogging.logger {}

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
        binding = ActivityItemListBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        logger.debug("OnCreate ItemListActivity")
        rvCountries = binding.itemList

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        binding.fab.setOnClickListener { view ->
            logger.debug("Snackbar action")
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val countryDao = CountryDb.getDatabase(applicationContext).countryDao()
        countryDao.getAll().value?.forEach { it -> countryList.add(it) }
        logger.debug("nb on initial countries: ${countryList.size}")

        setupRecyclerView(binding.itemList)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        logger.debug("setupRecyclerView")
        val countryDao = CountryDb.getDatabase(applicationContext).countryDao()
        recyclerView.adapter =
            SimpleItemRecyclerViewAdapter(
                this,
                countryDao.getAll().value!!
            )
    }

}
