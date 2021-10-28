package eu.remilapointe.country.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.country.R
import eu.remilapointe.country.entity.Country
import eu.remilapointe.country.ui.CountryViewHolder
import eu.remilapointe.country.ui.main.CountryViewModel
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CountryListAdapter internal constructor(
    context: Context,
    private val countryViewModel: CountryViewModel,
    private val clickListener: (Country) -> View.OnClickListener
) : RecyclerView.Adapter<CountryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var countriesList = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = inflater.inflate(R.layout.country_recyclerview_item, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val current = countriesList[position]
        logger.debug("${Country.ELEM} ${Country.PRIM_KEY}: ${current.id}, ${Country.NAMEID}: ${current.nameId}")
        holder.tvNameItem.text = current.nameId.toString()
        holder.tvCapitaleItem.text = current.capitaleId.toString()
        holder.tvAbbrevItem.text = current.abbrev
        holder.tvUEEntry.text = current.UEEntryIn.toString()
    }

    internal fun setCountries(strs: MutableList<Country>) {
        this.countriesList = strs
        logger.debug("set${Country.ELEM} with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Country {
        val country = countriesList[position]
        logger.debug("get ${Country.ELEM} position $position value ${country.nameId}")
        return country
    }

    override fun getItemCount(): Int = countriesList.size

}
