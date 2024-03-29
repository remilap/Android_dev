package eu.remilapointe.country

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import eu.remilapointe.country.databinding.ActivityItemDetailBinding
import eu.remilapointe.country.entity.Country
import mu.KotlinLogging
import java.time.LocalDate

private lateinit var binding: ActivityItemDetailBinding
private val logger = KotlinLogging.logger {}

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ItemDetailActivity : AppCompatActivity() {

    private var item: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        setSupportActionBar(binding.detailToolbar)
        logger.debug("Activity start")

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
        }
        Bundle().apply {
            val countryId = intent.getLongExtra(ARG_ITEM_ID, 0)
            item = Country(countryId,
                98,
                97,
                "TO",
                198,
                19,
                LocalDate.ofYearDay(1984, 1),
                LocalDate.ofYearDay(2004, 1),
                true,
                "to",
                1999,
                "2006"
            )
            item?.let {
                val txt = "Entry in UE in ${it.UEEntryIn}, language is ${it.languages}"
                binding.itemDetail.text = txt
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
