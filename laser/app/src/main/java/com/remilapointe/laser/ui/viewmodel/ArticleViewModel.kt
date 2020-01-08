package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.*
import com.remilapointe.laser.repo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepo: ArticleRepo
    private val produitRepo: ProduitRepo
    private val coloriRepo: ColoriRepo
    private val tailleRepo: TailleRepo
    private val placeLogoRepo: PlaceLogoRepo

    val allArticles: LiveData<MutableList<Article>>
    val allProduits: LiveData<MutableList<Produit>>
    val allColoris: LiveData<MutableList<Colori>>
    val allTailles: LiveData<MutableList<Taille>>
    val allPlaceLogs: LiveData<MutableList<PlaceLogo>>

    init {
        d("init")
        val articleDao = LaserRoomDatabase.getDatabase(application).articleDao()
        articleRepo = ArticleRepo(articleDao)
        allArticles = articleRepo.allArticles
        if (allArticles.value == null) {
            d("allArticlesAvecPrix, size: 0")
        } else {
            d("allArticlesAvecPrix, size: " + allArticles.value?.size)
        }
        val produitDao = LaserRoomDatabase.getDatabase(application).produitDao()
        produitRepo = ProduitRepo(produitDao)
        allProduits = produitRepo.allProduits
        val coloriDao = LaserRoomDatabase.getDatabase(application).coloriDao()
        coloriRepo = ColoriRepo(coloriDao)
        allColoris = coloriRepo.allColoris
        val tailleDao = LaserRoomDatabase.getDatabase(application).tailleDao()
        tailleRepo = TailleRepo(tailleDao)
        allTailles = tailleRepo.allTailles
        val placeLogoDao = LaserRoomDatabase.getDatabase(application).placeLogoDao()
        placeLogoRepo = PlaceLogoRepo(placeLogoDao)
        allPlaceLogs = placeLogoRepo.allPlaceLogos
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(proId: Int, colId: Int, taiId: Int, plaId: Int) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert ${Article.ELEM} ${Article.PRODUIT_ID}: $proId, ${Article.COLORI_ID}: $colId, ${Article.TAILLE_ID}: $taiId, ${Article.PLACELOGO_ID}: $plaId")
        articleRepo.insert(proId, colId, taiId, plaId)
    }

    fun remove(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model remove ${Article.ELEM} ${Article.PRIM_KEY}: ${article.id}")
        articleRepo.remove(article)
    }

    fun getAllArticlesAvecPrix() : Array<Article> {
        return Array(size = allArticles.value?.size!!) { i -> allArticles.value?.get(i)!! }
    }

    fun getArticleById(id: Int) : Article {
        getAllArticlesAvecPrix().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Article(0, 0, 0, 0, 0)
    }

    fun getProduitById(id: Int) : String? = allProduits.value!![id].elem

    fun getColoriById(id: Int) : String? = allColoris.value!![id].elem

    fun getTailleById(id: Int) : String? = allTailles.value!![id].elem

    fun getPlaceLogoById(id: Int) : String? = allPlaceLogs.value!![id].elem

    fun getProduitId(elem: String) : Int {
        allProduits.value!!.forEach { if (it.elem == elem) return it.id }
        return 0
    }

    fun getColoriId(elem: String) : Int {
        allColoris.value!!.forEach { if (it.elem == elem) return it.id }
        return 0
    }

    fun getTailleId(elem: String) : Int {
        allTailles.value!!.forEach { if (it.elem == elem) return it.id }
        return 0
    }

    fun getPlaceLogoId(elem: String) : Int {
        allPlaceLogs.value!!.forEach { if (it.elem == elem) return it.id }
        return 0
    }

}
