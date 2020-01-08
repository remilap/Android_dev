package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.dao.ArticleDao
import com.remilapointe.laser.db.*

class ArticleRepo(private val dao: ArticleDao) {

    val allArticles : LiveData<MutableList<Article>> = dao.getAll()

    @WorkerThread
    suspend fun insert(proId: Int, colId: Int, taiId: Int, plaId: Int) : Int {
        val art = get(proId, colId, taiId, plaId)
        if (art != null) {
            d("in Repo, don't insert ${Article.ELEM} that already exists")
            return art.id
        }
        val article = Article(0, proId, colId, taiId, plaId)
        d("in Repo insert ${Article.ELEM} id: ${article.id}")
        dao.insert(article)
        return article.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val article = allArticles.value?.get(pos)
        d("in Repo remove ${Article.ELEM} id: ${article!!.id}, produitId: ${article.produitId}")
        dao.remove(article.id)
    }

    @WorkerThread
    suspend fun remove(article: Article) {
        d("in Repo remove ${Article.ELEM} id: ${article.id}, produitId: ${article.produitId}")
        dao.remove(article.id)
    }

    @WorkerThread
    fun get(key: Int): Article? {
        d("in Repo get ${Article.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(proId: Int, colId: Int, taiId: Int, plaId: Int) : Article? {
        d("in Repo get ${Article.ELEM} with proId=$proId, colId=$colId, taiId=$taiId, plaId=$plaId")
        return dao.get(proId, colId, taiId, plaId)
    }

    @WorkerThread
    suspend fun update(article: Article) {
        d("in Repo update ${Article.ELEM} id: ${article.id}, produitId: ${article.produitId}")
        dao.update(article.id, article.produitId, article.coloriId, article.tailleId, article.placeLogoId)
    }

}
