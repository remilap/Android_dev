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
import com.remilapointe.laser.db.Produit
import com.remilapointe.laser.db.Taille

class ProduitListAdapter internal constructor(
    context: Context,
    private val clickListener: (Produit) -> View.OnClickListener
    ) : RecyclerView.Adapter<ProduitListAdapter.ProduitViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var produitList = mutableListOf<Produit>()
    private var coloriList = mutableListOf<Colori>()
    private var tailleList = mutableListOf<Taille>()
    private var placeLogoList = mutableListOf<PlaceLogo>()

    inner class ProduitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idItemView: TextView = itemView.findViewById(R.id.tvProduitId)
        val coloriItemView: TextView = itemView.findViewById(R.id.tvProduitColori)
        val tailleItemView: TextView = itemView.findViewById(R.id.tvProduitTaille)
        val placeLogoItemView: TextView = itemView.findViewById(R.id.tvProduitPlaceLogo)
        fun bind(produit: Produit, listener: (Produit) -> View.OnClickListener) = idItemView.setOnClickListener( listener(produit) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val itemView = inflater.inflate(R.layout.produit_recyclerview_item, parent, false)
        return ProduitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val current = produitList[position]
        holder.idItemView.text = current.id.toString()
        holder.coloriItemView.text = coloriList[current.coloriId].elem
        holder.tailleItemView.text = tailleList[current.tailleId].elem
        holder.placeLogoItemView.text = placeLogoList[current.placeLogoId].elem
        holder.bind(current, clickListener)
    }

    internal fun setProduits(strs: MutableList<Produit>) {
        this.produitList = strs
        d("setProduits with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setColoris(strs: MutableList<Colori>) {
        this.coloriList = strs
        d("setColoris with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setTailles(strs: MutableList<Taille>) {
        this.tailleList = strs
        d("setTailles with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setPlaceLogs(strs: MutableList<PlaceLogo>) {
        this.placeLogoList = strs
        d("setPlaceLogs with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Produit {
        val produit = produitList.get(position)
        d("get elem position $position value ${produit.id}")
        return produit
    }

    override fun getItemCount(): Int = produitList.size

}
