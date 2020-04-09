package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.dao.*
import com.remilapointe.laser.db.*

class ArticleRepo(
    private val articleDao: ArticleDao,
    private val produitDao: ProduitDao,
    private val coloriDao: ColoriDao,
    private val tailleDao: TailleDao,
    private val placeLogoDao: PlaceLogoDao
) {

    val allArticles : LiveData<MutableList<Article>> = articleDao.getAll()

    @WorkerThread
    suspend fun insert(proId: Int, colId: Int, taiId: Int, plaId: Int) : Int {
        val art = get(proId, colId, taiId, plaId)
        if (art != null) {
            d("in Repo, don't insert ${Article.ELEM} that already exists")
            return art.id
        }
        val article = Article(0, proId, colId, taiId, plaId)
        d("in Repo insert ${Article.ELEM} id: ${article.id}")
        articleDao.insert(article)
        return article.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val article = allArticles.value?.get(pos)
        d("in Repo remove ${Article.ELEM} id: ${article!!.id}, produitId: ${article.produitId}")
        return articleDao.remove(article.id)
    }

    @WorkerThread
    suspend fun remove(article: Article) : Int {
        d("in Repo remove ${Article.ELEM} id: ${article.id}, produitId: ${article.produitId}")
        return articleDao.remove(article.id)
    }

    @WorkerThread
    suspend fun get(key: Int): Article? {
        d("in Repo get ${Article.ELEM} id: $key")
        return articleDao.get(key)
    }

    @WorkerThread
    suspend fun get(proId: Int, colId: Int, taiId: Int, plaId: Int) : Article? {
        d("in Repo get ${Article.ELEM} with proId=$proId, colId=$colId, taiId=$taiId, plaId=$plaId")
        return articleDao.get(proId, colId, taiId, plaId)
    }

    @WorkerThread
    suspend fun update(article: Article) : Int {
        d("in Repo update ${Article.ELEM} id: ${article.id}, produitId: ${article.produitId}")
        return articleDao.update(article.id, article.produitId, article.coloriId, article.tailleId, article.placeLogoId)
    }

    @WorkerThread
    suspend fun getArticleString(key: Int) : String {
        d("in Repo, getArticleString id: ${key}")
        val article = get(key)
        val produit = produitDao.get(article!!.produitId)
        val colori = coloriDao.get(article.coloriId)
        val taille = tailleDao.get(article.tailleId)
        val placeLogo = placeLogoDao.get(article.placeLogoId)
        return produit.elem + "/" + colori.elem + "/" + taille.elem + "/" + placeLogo.elem
    }

    @WorkerThread
    suspend fun getAllArticlesString() : LiveData<MutableList<String>> {
        d("in Repo, getAllArticlesString")
        var res = LiveData<MutableList<String>> = mutableListOf<String>().to
        allArticles.value?.forEach {

        }
        return allArticles.value?.map { getArticleString(it.id) }
    }

}
