package com.remilapointe.country.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.remilapointe.country.entity.Country
import com.remilapointe.country.model.CountryViewHolder

class CountryAdapter(val clickKistener: ((Country) -> Unit)?) : PagedListAdapter<Country, CountryViewHolder>(
    diffCallback
) {

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bindTo(getItem(position)!!, clickKistener!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(parent)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                    oldItem == newItem
        }
    }

}
