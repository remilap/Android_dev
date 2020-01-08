package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.dao.StockDao
import com.remilapointe.laser.db.Stock

class StockRepo(private val dao: StockDao) {

    val allStocks: LiveData<MutableList<Stock>> = dao.getAll()

    @WorkerThread
    suspend fun insert(titreStock: String, artList: List<Int>) {
        val oneObj = Stock(0, titreStock, artList)
        d("in Repo, insert ${Stock.ELEM}, id= ${oneObj.id}")
        dao.insert(oneObj)
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val oneObj = allStocks.value?.get(pos)
        d("in Repo remove ${Stock.ELEM} id: ${oneObj!!.id}, titreStock: ${oneObj.titreStock}")
        dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(stock: Stock) {
        d("in Repo remove ${Stock.ELEM} id: ${stock.id}, titreStock: ${stock.titreStock}")
        dao.remove(stock.id)
    }

    @WorkerThread
    fun get(key: Int) : Stock{
        return dao.get(key)
    }

    @WorkerThread
    suspend fun update(stock: Stock) {
        d("in Repo update ${Stock.ELEM} id: ${stock.id}, titreStock: ${stock.titreStock}")
        dao.update(stock.id, stock.titreStock, stock.articlesEnStockList)
    }

}
