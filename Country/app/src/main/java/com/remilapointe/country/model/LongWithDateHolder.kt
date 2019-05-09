package com.remilapointe.country.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.country.R
import com.remilapointe.country.entity.LongWithDate
import org.jetbrains.anko.toast

class LongWithDateHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.longwithdate_short_item, parent, false)) {

    private val idView = itemView.findViewById<TextView>(R.id.lwd_id)
    lateinit var longWithDate : LongWithDate
    private val context = parent.context

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(longWithDate: LongWithDate, clickListener: ((LongWithDate) -> Unit)) {
        context.toast("binTo(longWithDate=" + longWithDate.id + ", val=" + longWithDate.value)
        this.longWithDate = longWithDate
        idView.text = longWithDate.id.toString()
        idView.setOnClickListener { clickListener(longWithDate) }
    }

}
