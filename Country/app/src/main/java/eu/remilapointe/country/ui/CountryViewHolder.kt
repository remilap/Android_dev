package eu.remilapointe.country.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.country.R

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvNameItem: TextView = itemView.findViewById(R.id.tvCountryNameItem)
    val tvCapitaleItem: TextView = itemView.findViewById(R.id.tvCountryCapitaleItem)
    val tvAbbrevItem: TextView = itemView.findViewById(R.id.tvCountryAbbrevItem)
    val tvUEEntry: TextView = itemView.findViewById(R.id.tvCountryUEEntryItem)
}
