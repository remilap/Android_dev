package com.remilapointe.laser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.ArticlesEnStock

class ArticlesEnStockListAdapter internal constructor(
    context: Context,
    private val clickListener: (ArticlesEnStock) -> View.OnClickListener
) : RecyclerView.Adapter<ArticlesEnStockListAdapter.ArticlesEnStockViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ArticlesEnStockList = mutableListOf<ArticlesEnStock>()

    inner class ArticlesEnStockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articlesEnStockView: TextView = itemView.findViewById(R.id.tvArticlesEnStockArticle)
        val nbArticlesEnStockView: TextView = itemView.findViewById(R.id.tvArticlesEnStockNb)
        fun bind(articlesEnStock: ArticlesEnStock, listener: (ArticlesEnStock) -> View.OnClickListener) = articlesEnStockView.setOnClickListener( listener(articlesEnStock) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesEnStockViewHolder {
        val itemView = inflater.inflate(R.layout.articlesenstock_recyclerview_item, parent, false)
        return ArticlesEnStockViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticlesEnStockViewHolder, position: Int) {
        val current = ArticlesEnStockList[position]
        holder.articlesEnStockView.text = current.articleId.toString() // TODO improve description article
        holder.nbArticlesEnStockView.text = current.nb.toString()
        holder.bind(current, clickListener)
    }

    internal fun setArticlesEnStock(strs: MutableList<ArticlesEnStock>) {
        this.ArticlesEnStockList = strs
        d("set${ArticlesEnStock.ELEM}s with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): ArticlesEnStock {
        val articlesEnStock = ArticlesEnStockList[position]
        d("get ${ArticlesEnStock.ELEM} position $position value $position")
        return articlesEnStock
    }

    override fun getItemCount(): Int = ArticlesEnStockList.size

}
