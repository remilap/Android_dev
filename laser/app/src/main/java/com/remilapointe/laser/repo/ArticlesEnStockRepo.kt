package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.ArticlesEnStock
import com.remilapointe.laser.dao.ArticlesEnStockDao

class ArticlesEnStockRepo(private val dao: ArticlesEnStockDao) {

    val allArticlesEnStock: LiveData<MutableList<ArticlesEnStock>> = dao.getAll()

    @WorkerThread
    suspend fun insert(artId: Int, nb: Int) {
        val articlesEnStock = ArticlesEnStock(0, artId, nb)
        d("in Repo insert ${ArticlesEnStock.ELEM} id: ${articlesEnStock.id}")
        dao.insert(articlesEnStock)
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val articlesEnStock = allArticlesEnStock.value?.get(pos)
        d("in Repo remove ${ArticlesEnStock.ELEM} id: ${articlesEnStock!!.id}, ${ArticlesEnStock.ARTICLE_ID}: ${articlesEnStock.articleId}")
        dao.remove(articlesEnStock.id)
    }

    @WorkerThread
    suspend fun remove(articlesEnStock: ArticlesEnStock) {
        d("in Repo remove ${ArticlesEnStock.ELEM} id: ${articlesEnStock.id}, ${ArticlesEnStock.ARTICLE_ID}: ${articlesEnStock.articleId}")
        dao.remove(articlesEnStock.id)
    }

    @WorkerThread
    fun get(key: Int) : ArticlesEnStock{
        return dao.get(key)
    }

    @WorkerThread
    suspend fun update(articlesEnStock: ArticlesEnStock) {
        d("in Repo update ${ArticlesEnStock.ELEM} id: ${articlesEnStock.id}, ${ArticlesEnStock.ARTICLE_ID}: ${articlesEnStock.articleId}")
        dao.update(articlesEnStock.id, articlesEnStock.articleId, articlesEnStock.nb)
    }

}
