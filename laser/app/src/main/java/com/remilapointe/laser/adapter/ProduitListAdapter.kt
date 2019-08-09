package com.remilapointe.laser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.Produit
import com.remilapointe.laser.ui.viewmodel.ColoriViewModel
import com.remilapointe.laser.ui.viewmodel.PlaceLogoViewModel
import com.remilapointe.laser.ui.viewmodel.TailleViewModel

class ProduitListAdapter internal constructor(
    context: Context,
    private val clickListener: (Produit) -> View.OnClickListener
    ) : RecyclerView.Adapter<ProduitListAdapter.ProduitViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var strList = mutableListOf<Produit>()

    inner class ProduitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idItemView: TextView = itemView.findViewById(R.id.tvProduitId)
        val coloriItemView: TextView = itemView.findViewById(R.id.tvProduitColori)
        val tailleItemView: TextView = itemView.findViewById(R.id.tvProduitTaille)
        val placeLogoItemView: TextView = itemView.findViewById(R.id.tvProduitPlaceLogo)
        fun bind(produit: Produit, listener: (Produit) -> View.OnClickListener) = coloriItemView.setOnClickListener( listener(produit) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val itemView = inflater.inflate(R.layout.produit_recyclerview_item, parent, false)
        return ProduitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val current = strList[position]
        holder.idItemView.text = current.id.toString()
        //holder.coloriItemView.text = coloriViewModel.getValueForId(current.coloriId)
        //holder.tailleItemView.text = tailleViewModel.getValueForId(current.tailleId)
        //holder.placeLogoItemView.text = placeLogoViewModel.getValueForId(current.placeLogoId)
        holder.bind(current, clickListener)
    }

    internal fun setStrings(strs: MutableList<Produit>) {
        this.strList = strs
        d("setStrings with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Produit {
        val produit = strList.get(position)
        d("get elem position $position value ${produit.id}")
        return produit
    }

    override fun getItemCount(): Int = strList.size

}
