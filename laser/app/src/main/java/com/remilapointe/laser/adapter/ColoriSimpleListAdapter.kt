package com.remilapointe.laser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.Colori

class ColoriSimpleListAdapter internal constructor(
    context: Context
    ) : RecyclerView.Adapter<ColoriSimpleListAdapter.ColoriViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var colorisList = mutableListOf<Colori>()

    inner class ColoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringItemView: TextView = itemView.findViewById(R.id.tvColoriItem)
//        fun bind(colori: Colori, listener: (Colori) -> View.OnClickListener) = stringItemView.setOnClickListener( listener(colori) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColoriViewHolder {
        val itemView = inflater.inflate(R.layout.colori_recyclerview_item, parent, false)
        return ColoriViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ColoriViewHolder, position: Int) {
        val current = colorisList[position]
        holder.stringItemView.text = current.elem
//        holder.bind(current, clickListener)
    }

    internal fun setColoris(strs: MutableList<Colori>) {
        this.colorisList = strs
        d("setColoris with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Colori {
        val colori = colorisList.get(position)
        d("get Colori position $position value ${colori.elem}")
        return colori
    }

    override fun getItemCount(): Int = colorisList.size

}
