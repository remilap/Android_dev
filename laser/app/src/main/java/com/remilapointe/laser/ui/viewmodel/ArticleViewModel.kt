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

    var allArticles: LiveData<MutableList<Article>>
    var allProduits: LiveData<MutableList<Produit>>
    var allColoris: LiveData<MutableList<Colori>>
    var allTailles: LiveData<MutableList<Taille>>
    var allPlaceLogos: LiveData<MutableList<PlaceLogo>>

    init {
        d("init")
        val articleDao = LaserRoomDatabase.getDatabase(application).articleDao()
        articleRepo = ArticleRepo(articleDao)
        allArticles = articleRepo.allArticles
        if (allArticles.value == null) {
            d("all${Article.ELEM}s, size: 0")
        } else {
            d("all${Article.ELEM}s, size: " + allArticles.value?.size)
        }
        val produitDao = LaserRoomDatabase.getDatabase(application).produitDao()
        produitRepo = ProduitRepo(produitDao)
        allProduits = produitRepo.allProduits
        if (allProduits.value == null) {
            d("all${Produit.ELEM}s, size: 0")
        } else {
            d("all${Produit.ELEM}s, size: " + allProduits.value?.size)
        }
        val coloriDao = LaserRoomDatabase.getDatabase(application).coloriDao()
        coloriRepo = ColoriRepo(coloriDao)
        allColoris = coloriRepo.allColoris
        if (allColoris.value == null) {
            d("all${Colori.ELEM}s, size: 0")
        } else {
            d("all${Colori.ELEM}s, size: " + allColoris.value?.size)
        }
        val tailleDao = LaserRoomDatabase.getDatabase(application).tailleDao()
        tailleRepo = TailleRepo(tailleDao)
        allTailles = tailleRepo.allTailles
        if (allTailles.value == null) {
            d("all${Taille.ELEM}s, size: 0")
        } else {
            d("all${Taille.ELEM}s, size: " + allTailles.value?.size)
        }
        val placeLogoDao = LaserRoomDatabase.getDatabase(application).placeLogoDao()
        placeLogoRepo = PlaceLogoRepo(placeLogoDao)
        allPlaceLogos = placeLogoRepo.allPlaceLogos
        if (allPlaceLogos.value == null) {
            d("all${PlaceLogo.ELEM}s, size: 0")
        } else {
            d("all${PlaceLogo.ELEM}s, size: " + allPlaceLogos.value?.size)
        }
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

    inline fun <reified T> getAllElems(l: LiveData<MutableList<T>>) : Array<T> {
        var res = emptyArray<T>()
        val sb = StringBuffer()
        l.value?.forEach {
            res = res.plus(it)
            sb.append(it.toString()).append(", ")
        }
        d("getAllElems for ${T::class.simpleName}: size=${res.size}, $sb")
        return res
    }

    inline fun <reified T: IdValue> getAllElemsString(l: LiveData<MutableList<T>>) : ArrayList<String> {
        val res = arrayListOf<String>()
        val sb = StringBuffer()
        l.value?.forEach {
            res.add(it.elem)
            sb.append(it.elem).append(", ")
        }
        d("getAllElemsString for ${T::class.simpleName}: $sb")
        return res
    }

    inline fun <reified T: IdValue> getElemStringById(l: LiveData<MutableList<T>>, id: Int) : String? {
        getAllElems(l).forEach { if (it.id == id) {
            d("getElemStringById for ${T::class.simpleName} elem found for id $id: ${it.elem}")
            return it.elem
        } }
        d("getElemStringById for ${T::class.simpleName} elem not found for id $id")
        return ""
    }

    inline fun <reified T: IdValue> getElemId(l: LiveData<MutableList<T>>, elem: String) : Int {
        l.value!!.forEach { if (it.elem == elem) {
            d("getElemId for ${T::class.simpleName} id found for id $elem: ${it.id}")
            return it.id
        } }
        d("getElemId for ${T::class.simpleName} id not found for elem $elem")
        return 0
    }

    fun getAllArticles() : Array<Article> = getAllElems(allArticles)

    fun getArticleById(id: Int) : Article? {
        getAllArticles().forEach {if (it.id == id) {
            d("${Article.ELEM} found for id=$id, $it")
            return it
        }}
        d("${Article.ELEM} not found for id=$id")
        return null
    }

    fun getAllProduits() : Array<Produit> = getAllElems(allProduits)

    fun getAllProduitsString() : ArrayList<String> = getAllElemsString(allProduits)

    fun getProduitStringById(id: Int) : String? = getElemStringById(allProduits, id)

    fun getProduitId(elem: String) : Int = getElemId(allProduits, elem)

    fun getAllColoris() : Array<Colori> = getAllElems(allColoris)

    fun getAllColorisString() : ArrayList<String> = getAllElemsString(allColoris)

    fun getColoriById(id: Int) : String? = getElemStringById(allColoris, id)

    fun getColoriId(elem: String) : Int = getElemId(allColoris, elem)

    fun getAllTailles() : Array<Taille> = getAllElems(allTailles)

    fun getAllTaillesString() : ArrayList<String> = getAllElemsString(allTailles)

    fun getTailleById(id: Int) : String? = getElemStringById(allTailles, id)

    fun getTailleId(elem: String) : Int = getElemId(allTailles, elem)

    fun getAllPlaceLogos() : Array<PlaceLogo> = getAllElems(allPlaceLogos)

    fun getAllPlaceLogosString() : ArrayList<String> = getAllElemsString(allPlaceLogos)

    fun getPlaceLogoById(id: Int) : String? = getElemStringById(allPlaceLogos, id)

    fun getPlaceLogoId(elem: String) : Int = getElemId(allPlaceLogos, elem)

    fun getStringForArticleId(id: Int) : String? {
        val article = getArticleById(id)
        val res = StringBuffer()
        res.append(getProduitStringById(article!!.produitId))
        res.append("/")
        res.append(getColoriById(article.coloriId))
        res.append("/")
        res.append(getTailleById(article.tailleId))
        res.append("/")
        res.append(getPlaceLogoById(article.placeLogoId))
        return res.toString()
    }

}
