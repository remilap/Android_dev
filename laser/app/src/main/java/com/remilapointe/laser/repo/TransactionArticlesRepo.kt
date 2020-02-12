package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.dao.TransactionArticlesDao
import com.remilapointe.laser.db.TransactionArticles
import java.time.LocalDate
import java.util.*

class TransactionArticlesRepo(private val dao: TransactionArticlesDao) {

    val allTransactionArticles : LiveData<MutableList<TransactionArticles>> = dao.getAll()

    @WorkerThread
    suspend fun insert(artId: Int, trType: String, nb: Int, prixAchatHt: Double, tva: Double, remise: Double, date: LocalDate) : Int {
        val transactionArticles = TransactionArticles(0, artId, trType, nb, prixAchatHt, tva, remise, date)
        d("in Repo insert ${TransactionArticles.ELEM} id: ${transactionArticles.id}")
        dao.insert(transactionArticles)
        return transactionArticles.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val transactionArticles = allTransactionArticles.value?.get(pos)
        d("in Repo remove ${TransactionArticles.ELEM} id: ${transactionArticles!!.id}, nb: ${transactionArticles.nb}")
        return dao.remove(transactionArticles.id)
    }

    @WorkerThread
    suspend fun remove(transactionArticles: TransactionArticles) : Int {
        d("in Repo remove ${TransactionArticles.ELEM} id: ${transactionArticles.id}, nb: ${transactionArticles.nb}")
        return dao.remove(transactionArticles.id)
    }

    @WorkerThread
    fun get(key: Int) : TransactionArticles{
        return dao.get(key)
    }

    @WorkerThread
    suspend fun update(transactionArticles: TransactionArticles) : Int {
        d("in Repo update ${TransactionArticles.ELEM} id: ${transactionArticles.id}, nb: ${transactionArticles.nb}")
        return dao.update(transactionArticles.id, transactionArticles.articleId, transactionArticles.trType, transactionArticles.nb, transactionArticles.prixHT, transactionArticles.TVA, transactionArticles.remise, transactionArticles.date)
    }

}
