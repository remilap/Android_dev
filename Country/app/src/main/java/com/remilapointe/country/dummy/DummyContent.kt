package eu.remilapointe.country.dummy

import android.content.Context
import eu.remilapointe.country.db.CountryDb
import eu.remilapointe.country.entity.Country
import eu.remilapointe.country.repository.CountryRepo
import kotlinx.coroutines.*
import org.jetbrains.anko.async
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Country> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<Long, Country> = HashMap()

    init {
        // Add some sample items.
        /**
        var country = Country(1, "FR", 1972)
        addItem(country)
        country = Country(2, "SP", 1972, "espagnol")
        addItem(country)
        country = Country(3, "DE", 1972, "allemand")
        addItem(country)
        country = Country(4, "BE", 1972, "flamand,français")
        addItem(country)
        country = Country(5, "CH", 1972, "français,allemand")
        addItem(country)
        **/
    }

    fun addItem(item: Country) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

}
