package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.ArticlesEnStock
import com.remilapointe.laser.dao.ArticlesEnStockDao
import java.time.LocalDate
import java.util.*

class ArticlesEnStockRepo(private val dao: ArticlesEnStockDao) {

    val allArticlesEnStock: LiveData<MutableList<ArticlesEnStock>> = dao.getAll()

    @WorkerThread
    suspend fun insert(artId: Int, nb: Int, nbAchetes: Int, dateAchat: LocalDate, prixAchat: Double): Int {
        val articlesEnStock = ArticlesEnStock(0, artId, nb, nbAchetes, dateAchat, prixAchat)
        d("in Repo insert ${ArticlesEnStock.ELEM} id: ${articlesEnStock.id}")
        dao.insert(articlesEnStock)
        return articlesEnStock.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int): Int {
        val articlesEnStock = allArticlesEnStock.value?.get(pos)
        d("in Repo remove ${ArticlesEnStock.ELEM} id: ${articlesEnStock!!.id}, ${ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID}: ${articlesEnStock.articleId}")
        return dao.remove(articlesEnStock.id)
    }

    @WorkerThread
    suspend fun remove(articlesEnStock: ArticlesEnStock): Int {
        d("in Repo remove ${ArticlesEnStock.ELEM} id: ${articlesEnStock.id}, ${ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID}: ${articlesEnStock.articleId}")
        return dao.remove(articlesEnStock.id)
    }

    @WorkerThread
    fun get(key: Int) : ArticlesEnStock{
        return dao.get(key)
    }

    @WorkerThread
    suspend fun update(artEnStock: ArticlesEnStock): Int {
        d("in Repo update ${ArticlesEnStock.ELEM} id: ${artEnStock.id}, ${ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID}: ${artEnStock.articleId}")
        return dao.update(artEnStock.id, artEnStock.articleId, artEnStock.nb, artEnStock.nbAchetes, artEnStock.dateAchat, artEnStock.prixAchatHT)
    }

    @WorkerThread
    suspend fun sortieArticles(artEnStock: ArticlesEnStock, nbs: Int) : Boolean {
        d("in Repo sortieArticles ${ArticlesEnStock.ELEM} id: ${artEnStock.id}, ${ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID}: ${artEnStock.articleId}")
        val nBefore = artEnStock.nb
        var nAfter = nBefore
        var nbUpd = 0
        if (artEnStock.nb >= nbs) {
            nbUpd = dao.update(artEnStock.id, artEnStock.articleId, artEnStock.nb - nbs, artEnStock.nbAchetes, artEnStock.dateAchat, artEnStock.prixAchatHT)
            val artEnStockUpd = dao.get(artEnStock.id)
            nAfter = artEnStockUpd.nb
        }
        return (nbUpd > 0 && nAfter == nBefore - nbs)
    }

    @WorkerThread
    fun getNbArticlesEnStock(key: Int): Int {
        return dao.getNbArticlesEnStock(key)
    }

}
