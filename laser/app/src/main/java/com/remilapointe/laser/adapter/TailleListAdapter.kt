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
import com.remilapointe.laser.db.PlaceLogo
import com.remilapointe.laser.db.Taille

class TailleListAdapter internal constructor(
    context: Context,
    private val clickListener: (Taille) -> View.OnClickListener
) : RecyclerView.Adapter<TailleListAdapter.TailleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var strList = mutableListOf<Taille>()

    inner class TailleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringItemView: TextView = itemView.findViewById(R.id.tvTailleItem)
        fun bind(taille: Taille, listener: (Taille) -> View.OnClickListener) = stringItemView.setOnClickListener( listener(taille) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TailleViewHolder {
        val itemView = inflater.inflate(R.layout.taille_recyclerview_item, parent, false)
        return TailleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TailleViewHolder, position: Int) {
        val current = strList[position]
        holder.stringItemView.text = current.elem
        holder.bind(current, clickListener)
    }

    internal fun setStrings(strs: MutableList<Taille>) {
        this.strList = strs
        d("setStrings with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Taille {
        val taille = strList.get(position)
        d("get elem position $position value ${taille.elem}")
        return taille
    }

    override fun getItemCount(): Int = strList.size

}
