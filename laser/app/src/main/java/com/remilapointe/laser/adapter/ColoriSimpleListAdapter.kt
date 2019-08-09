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
    private var strList = mutableListOf<Colori>()

    inner class ColoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColoriViewHolder {
        val itemView = inflater.inflate(R.layout.colori_recyclerview_item, parent, false)
        return ColoriViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ColoriViewHolder, position: Int) {
    }

    internal fun setStrings(strs: MutableList<Colori>) {
        this.strList = strs
        d("ColoriSimpleListAdapter:setStrings with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Colori {
        val colori = strList.get(position)
        d("ColoriSimpleListAdapter: get elem position $position value ${colori.elem}")
        return colori
    }

    override fun getItemCount(): Int = strList.size

}
