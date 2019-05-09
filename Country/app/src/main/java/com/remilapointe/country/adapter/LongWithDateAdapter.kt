package com.remilapointe.country.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.remilapointe.country.entity.LongWithDate
import com.remilapointe.country.model.LongWithDateHolder

class LongWithDateAdapter(private val clickListener : ((LongWithDate) -> Unit)?) : PagedListAdapter<LongWithDate, LongWithDateHolder>(
    diffCallback) {

    override fun onBindViewHolder(holder: LongWithDateHolder, position: Int) {
        holder.bindTo(getItem(position)!!, clickListener!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LongWithDateHolder =
        LongWithDateHolder(parent)

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
        private val diffCallback = object : DiffUtil.ItemCallback<LongWithDate>() {
            override fun areItemsTheSame(oldItem: LongWithDate, newItem: LongWithDate): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LongWithDate, newItem: LongWithDate): Boolean =
                oldItem.date == newItem.date

        }
    }
}
