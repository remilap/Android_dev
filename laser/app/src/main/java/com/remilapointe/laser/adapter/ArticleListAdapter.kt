package com.remilapointe.laser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.*

class ArticleListAdapter internal constructor(
    context: Context,
    private val clickListener: (Article) -> View.OnClickListener
    ) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var articlesList = mutableListOf<Article>()
    private var produitsList = mutableListOf<Produit>()
    private var colorisList = mutableListOf<Colori>()
    private var taillesList = mutableListOf<Taille>()
    private var placeLogosList = mutableListOf<PlaceLogo>()

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idItemView: TextView = itemView.findViewById(R.id.tvProduitItem)
        val produitItemView: TextView = itemView.findViewById(R.id.tvArticleProduit)
        val coloriItemView: TextView = itemView.findViewById(R.id.tvArticleColori)
        val tailleItemView: TextView = itemView.findViewById(R.id.tvArticleTaille)
        val placeLogoItemView: TextView = itemView.findViewById(R.id.tArticlePlaceLogo)
        fun bind(article: Article, listener: (Article) -> View.OnClickListener) = idItemView.setOnClickListener( listener(article) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = inflater.inflate(R.layout.article_recyclerview_item, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val current = articlesList[position]
        d("${produitsList.size} articles, ${colorisList.size} coloris, ${taillesList.size} tailles, ${placeLogosList.size} placeLogos")
        d("produit id: ${current.id}, coloriId: ${current.coloriId}, tailleId: ${current.tailleId}, placelogoId: ${current.placeLogoId}")
        holder.idItemView.text = current.id.toString()
        produitsList.forEach {
            if (it.id == current.produitId) {
                holder.produitItemView.text = it.elem
            }
        }
        colorisList.forEach {
            if (it.id == current.coloriId) {
                holder.coloriItemView.text = it.elem
            }
        }
        taillesList.forEach {
            if (it.id == current.tailleId) {
                holder.tailleItemView.text = it.elem
            }
        }
        placeLogosList.forEach {
            if (it.id == current.placeLogoId) {
                holder.placeLogoItemView.text = it.elem
            }
        }
        holder.bind(current, clickListener)
    }

    internal fun setArticlesAvecPrix(strs: MutableList<Article>) {
        this.articlesList = strs
        d("set${Article.ELEM} with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setProduits(strs: MutableList<Produit>) {
        this.produitsList = strs
        d("set${Produit.ELEM} with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setColoris(strs: MutableList<Colori>) {
        this.colorisList = strs
        d("set${Colori.ELEM}s with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setTailles(strs: MutableList<Taille>) {
        this.taillesList = strs
        d("set${Taille.ELEM}s with ${strs.size} elems")
        notifyDataSetChanged()
    }

    internal fun setPlaceLogs(strs: MutableList<PlaceLogo>) {
        this.placeLogosList = strs
        d("set${PlaceLogo.ELEM}s with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Article {
        val article = articlesList.get(position)
        d("get ${Article.ELEM} position $position value ${article.id}")
        return article
    }

    override fun getItemCount(): Int = articlesList.size

}
