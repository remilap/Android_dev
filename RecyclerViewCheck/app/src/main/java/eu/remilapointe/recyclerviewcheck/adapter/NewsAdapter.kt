package eu.remilapointe.recyclerviewcheck.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.recyclerviewcheck.R
import eu.remilapointe.recyclerviewcheck.model.Article
import eu.remilapointe.recyclerviewcheck.ui.NewsViewHolder

class NewsAdapter(var context: Context, var articleList: List<Article>) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articleList[position]
        holder.setSourceName(article.source.name)
        holder.setTitle(article.title)
        holder.setDate(article.publishedAt)
        holder.setThumbnail(context, article.urlToImage)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}
