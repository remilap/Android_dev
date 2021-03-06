package com.remilapointe.laser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.PlaceLogo

class PlaceLogoListAdapter internal constructor(
    context: Context,
    private val clickListener: (PlaceLogo) -> View.OnClickListener
) : RecyclerView.Adapter<PlaceLogoListAdapter.PlaceLogoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var placeLogosList = mutableListOf<PlaceLogo>()

    inner class PlaceLogoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringItemView: TextView = itemView.findViewById(R.id.tvPlaceLogoItem)
        fun bind(placeLogo: PlaceLogo, listener: (PlaceLogo) -> View.OnClickListener) = stringItemView.setOnClickListener( listener(placeLogo) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceLogoViewHolder {
        val itemView = inflater.inflate(R.layout.placelogo_recyclerview_item, parent, false)
        return PlaceLogoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceLogoViewHolder, position: Int) {
        val current = placeLogosList[position]
        holder.stringItemView.text = current.elem
        holder.bind(current, clickListener)
    }

    internal fun setPlaceLogos(strs: MutableList<PlaceLogo>) {
        this.placeLogosList = strs
        d("setPlaceLogos with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): PlaceLogo {
        val placeLogo = placeLogosList.get(position)
        d("get PlaceLogo position $position value ${placeLogo.elem}")
        return placeLogo
    }

    override fun getItemCount(): Int = placeLogosList.size

}
