package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.LaserRoomDatabase
import com.remilapointe.laser.db.ArticlesEnStock
import com.remilapointe.laser.repo.ArticlesEnStockRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesEnStockViewModel(application: Application) : AndroidViewModel(application) {

    private val articlesEnStockRepo: ArticlesEnStockRepo

    val allArticlesEnStock: LiveData<MutableList<ArticlesEnStock>>

    init {
        val articlesEnStockDao = LaserRoomDatabase.getDatabase(application).articlesEnStockDao()
        articlesEnStockRepo = ArticlesEnStockRepo(articlesEnStockDao)
        allArticlesEnStock = articlesEnStockRepo.allArticlesEnStock
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(articleId: Int, nb: Int) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert ${ArticlesEnStock.ELEM} ${ArticlesEnStock.ARTICLE_ID}: $articleId, ${ArticlesEnStock.NB}: $nb")
        articlesEnStockRepo.insert(articleId, nb)
    }

    fun remove(articlesEnStock: ArticlesEnStock) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model remove ${ArticlesEnStock.ELEM} ${ArticlesEnStock.PRIM_KEY}: ${articlesEnStock.id}")
        articlesEnStockRepo.remove(articlesEnStock)
    }

    fun getAllArticlesEnStock() : Array<ArticlesEnStock> =
        Array(size = allArticlesEnStock.value?.size!!) { i -> allArticlesEnStock.value?.get(i)!! }

    fun getArticlesEnStockById(id: Int) : ArticlesEnStock {
        getAllArticlesEnStock().forEach {
            if (it.id == id) {
                return it
            }
        }
        return ArticlesEnStock(0, 0, 0)
    }

}
