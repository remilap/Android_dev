package com.remilapointe.country.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.country.R
import com.remilapointe.country.entity.Country

class CountryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.country_short_item, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.name_fr)
    var country : Country? = null
    private val context = parent.context

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(country: Country, clickListener: ((Country) -> Unit)) {
        //context.toast("binTo(country="+country.name_fr)
        this.country = country
        nameView.text = country.abbrev
        nameView.setOnClickListener { clickListener(country) }
    }

}
