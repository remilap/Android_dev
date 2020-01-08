package com.remilapointe.laser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.Produit

class ProduitListAdapter internal constructor(
    context: Context,
    private val clickListener: (Produit) -> View.OnClickListener
) : RecyclerView.Adapter<ProduitListAdapter.ProduitViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var produitsList = mutableListOf<Produit>()

    inner class ProduitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringItemView: TextView = itemView.findViewById(R.id.tvProduitItem)
        fun bind(produit: Produit, listener: (Produit) -> View.OnClickListener) = stringItemView.setOnClickListener( listener(produit) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val itemView = inflater.inflate(R.layout.produit_recyclerview_item, parent, false)
        return ProduitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val current = produitsList[position]
        holder.stringItemView.text = current.elem
        holder.bind(current, clickListener)
    }

    internal fun setProduits(strs: MutableList<Produit>) {
        this.produitsList = strs
        d("set${Produit.ELEM}s with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Produit {
        val produit = produitsList[position]
        d("get ${Produit.ELEM} position $position value ${produit.elem}")
        return produit
    }

    override fun getItemCount(): Int = produitsList.size

}
