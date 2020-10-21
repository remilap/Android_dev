package eu.remilapointe.country.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.country_recyclerview_item.view.*

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvNameItem: TextView = itemView.tvCountryNameItem
    val tvCapitaleItem: TextView = itemView.tvCountryCapitaleItem
    val tvAbbrevItem: TextView = itemView.tvCountryAbbrevItem
    val tvUEEntry: TextView = itemView.tvCountryUEEntryItem
}
