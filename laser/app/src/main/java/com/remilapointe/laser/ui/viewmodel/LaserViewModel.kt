package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.*
import com.log4k.d
import com.remilapointe.laser.db.*
import com.remilapointe.laser.repo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class LaserViewModel(application: Application) : AndroidViewModel(application) {

    private val produitRepo: ProduitRepo
    private val coloriRepo: ColoriRepo
    private val tailleRepo: TailleRepo
    private val placeLogoRepo: PlaceLogoRepo
    private val articleRepo: ArticleRepo
    private val articlesEnStockRepo: ArticlesEnStockRepo
    private val transactionArticleRepo: TransactionArticlesRepo

    val allProduits: LiveData<MutableList<Produit>>
    val allColoris: LiveData<MutableList<Colori>>
    val allTailles: LiveData<MutableList<Taille>>
    val allPlaceLogos: LiveData<MutableList<PlaceLogo>>
    val allArticles: LiveData<MutableList<Article>>
//    val allArticlesString: LiveData<MutableList<String>> = Transformations.switchMap(allArticles, )
    val allArticlesEnStock: LiveData<MutableList<ArticlesEnStock>>
    var allTransactionArticles: LiveData<MutableList<TransactionArticles>>

//    private fun articleToString(arts: MutableList<Article>) = articleRepo.getArticleString()

    private val CL = this::class.java.simpleName

    init {
        d("$CL:init")

        /**
         * ### PRODUIT ###
         */
        val produitDao = LaserRoomDatabase.getDatabase(application).produitDao()
        produitRepo = ProduitRepo(produitDao)
        allProduits = produitRepo.allProduits
        if (allProduits.value == null) {
            d("$CL:all${Produit.ELEM}, size: 0")
        } else {
            d("$CL:all${Produit.ELEM}, size: " + allProduits.value?.size)
        }

        /**
         * ### COLORI ###
         */
        val coloriDao = LaserRoomDatabase.getDatabase(application).coloriDao()
        coloriRepo = ColoriRepo(coloriDao)
        allColoris = coloriRepo.allColoris
        if (allColoris.value == null) {
            d("$CL:all${Colori.ELEM}, size: 0")
        } else {
            d("$CL:all${Colori.ELEM}, size: " + allColoris.value?.size)
        }

        /**
         * ### TAILLE ###
         */
        val tailleDao = LaserRoomDatabase.getDatabase(application).tailleDao()
        tailleRepo = TailleRepo(tailleDao)
        allTailles = tailleRepo.allTailles
        if (allTailles.value == null) {
            d("$CL:all${Taille.ELEM}, size: 0")
        } else {
            d("$CL:all${Taille.ELEM}, size: " + allTailles.value?.size)
        }

        /**
         * ### PLACELOGO ###
         */
        val placeLogoDao = LaserRoomDatabase.getDatabase(application).placeLogoDao()
        placeLogoRepo = PlaceLogoRepo(placeLogoDao)
        allPlaceLogos = placeLogoRepo.allPlaceLogos
        if (allPlaceLogos.value == null) {
            d("$CL:all${PlaceLogo.ELEM}, size: 0")
        } else {
            d("$CL:all${PlaceLogo.ELEM}, size: " + allPlaceLogos.value?.size)
        }

        /**
         * ### ARTICLE ###
         */
        val articleDao = LaserRoomDatabase.getDatabase(application).articleDao()
        articleRepo = ArticleRepo(articleDao, produitDao, coloriDao, tailleDao, placeLogoDao)
        allArticles = articleRepo.allArticles
        if (allArticles.value == null) {
            d("$CL:all${Article.ELEM}s, size: 0")
        } else {
            d("$CL:all${Article.ELEM}s, size: " + allArticles.value?.size)
//            allArticlesString = allArticles.value?.map { getProduitStringById(it.produitId) + "/" + getColoriStringById(it.coloriId) + "/" + getTailleStringById(it.tailleId) + "/" + getPlaceLogoStringById(it.placeLogoId) }
        }

        /**
         * ### ARTICLESENSTOCK ###
         */
        val articlesEnStockDao = LaserRoomDatabase.getDatabase(application).articlesEnStockDao()
        articlesEnStockRepo = ArticlesEnStockRepo(articlesEnStockDao)
        allArticlesEnStock = articlesEnStockRepo.allArticlesEnStock
        if (allArticlesEnStock.value == null) {
            d("$CL:all${ArticlesEnStock.ELEM}s, size: 0")
        } else {
            d("$CL:all${ArticlesEnStock.ELEM}s, size: " + allArticlesEnStock.value?.size)
        }

        /**
         * ### TRANSACTIONARTICLE ###
         */
        val transactionArticleDao = LaserRoomDatabase.getDatabase(application).transactionArticlesDao()
        transactionArticleRepo = TransactionArticlesRepo(transactionArticleDao)
        allTransactionArticles = transactionArticleRepo.allTransactionArticles
        if (allTransactionArticles.value == null) {
            d("$CL:all${Article.ELEM}s, size: 0")
        } else {
            d("$CL:all${Article.ELEM}s, size: " + allTransactionArticles.value?.size)
        }

    }


    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    inline fun <reified T> getAllElems(l: LiveData<MutableList<T>>) : Array<T> {
        var res = emptyArray<T>()
        viewModelScope.launch(Dispatchers.IO) {
            val sb = StringBuffer()
            l.value?.forEach {
                res = res.plus(it)
                sb.append(it.toString()).append(", ")
            }
            d("getAllElems for ${T::class.simpleName}: size=${res.size}, $sb")
        }
        return res
    }

    inline fun <reified T: IdValue> getAllElemsString(l: LiveData<MutableList<T>>) : ArrayList<String> {
        val res = arrayListOf<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val sb = StringBuffer()
            l.value?.forEach {
                res.add(it.elem)
                sb.append(it.elem).append(", ")
            }
            d("getAllElemsString for ${T::class.simpleName}: $sb")
        }
        return res
    }

    inline fun <reified T: IdValue> getElemStringById(l: LiveData<MutableList<T>>, id: Int) : String? {
        var res = ""
        viewModelScope.launch(Dispatchers.IO) {
            getAllElems(l).forEach {
                if (it.id == id) {
                    d("getElemStringById for ${T::class.simpleName} elem found for id $id: ${it.elem}")
                    res = it.elem
                }
            }
            d("getElemStringById for ${T::class.simpleName} elem not found for id $id")
        }
        return res
    }

    inline fun <reified T: IdValue> getElemId(l: LiveData<MutableList<T>>, elem: String) : Int {
        var res = 0
        viewModelScope.launch(Dispatchers.IO) {
            l.value!!.forEach {
                if (it.elem == elem) {
                    d("getElemId for ${T::class.simpleName} id found for id $elem: ${it.id}")
                    res = it.id
                }
            }
            d("getElemId for ${T::class.simpleName} id not found for elem $elem")
        }
        return res
    }

    /**
     * ### PRODUIT ###
     */
    fun insertProduit(produit: String) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: insert ${Produit.ELEM}: $produit")
        produitRepo.insert(produit)
    }

    fun removeProduit(produit: Produit) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${Produit.ELEM}: ${produit.elem}")
        produitRepo.remove(produit)
    }

    fun updateProduit(produit: Produit) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${Produit.ELEM} ${Produit.PRIM_KEY}: ${produit.id} with value ${produit.elem}")
        produitRepo.update(produit)
    }

    fun getAllProduits() : Array<Produit> = getAllElems(allProduits)

    fun getAllProduitsString() : ArrayList<String> = getAllElemsString(allProduits)

    fun getProduitStringById(id: Int) : String? = getElemStringById(allProduits, id)

    fun getProduitId(elem: String) : Int = getElemId(allProduits, elem)

    fun getProduitById(id: Int) : Produit {
        getAllProduits().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Produit(0, "0")
    }

    fun getProduitValueForId(id: Int) : String = getProduitById(id).elem

    fun getProduitIdForValue(elem: String) : Int {
        getAllProduits().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

    /**
     * ### COLORI ###
     */
    fun insertColori(colori: String) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: insert ${Colori.ELEM}: $colori")
        coloriRepo.insert(colori)
    }

    fun removeColori(colori: Colori) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${Colori.ELEM}: ${colori.elem}")
        coloriRepo.remove(colori)
    }

    fun updateColori(colori: Colori) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${Colori.ELEM} ${Colori.PRIM_KEY}: ${colori.id} with value ${colori.elem}")
        coloriRepo.update(colori)
    }

//    fun getAllColoris() : Array<Colori> = getAllElems(allColoris)
    fun getAllColoris() : Array<Colori> =
        Array(size = allColoris.value?.size!!) { i -> allColoris.value?.get(i)!! }

    fun getAllColorisString() : ArrayList<String> = getAllElemsString(allColoris)

//    fun getColoriStringById(id: Int) : String? = getElemStringById(allColoris, id)
    fun getColoriStringById(id: Int) : String? {
    d("getColoriStringById: id=$id, allColoris size=${allColoris.value?.size}")
        allColoris.value?.forEach {
            if (it.id == id) {
                return it.elem
            }
        }
        return null
    }

    fun getColoriId(elem: String) : Int = getElemId(allColoris, elem)

    fun getColoriById(id: Int) : Colori {
        getAllColoris().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Colori(0, "0")
    }

    fun getColoriValueForId(id: Int) : String = getColoriById(id).elem

    fun getColoriIdForValue(elem: String) : Int {
        getAllColoris().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

    /**
     * ### TAILLE ###
     */
    fun insertTaille(taille: String) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: insert ${Taille.ELEM}: $taille")
        tailleRepo.insert(taille)
    }

    fun removeTaille(taille: Taille) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${Taille.ELEM}: ${taille.elem}")
        tailleRepo.remove(taille)
    }

    fun updateTaille(taille: Taille) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${Taille.ELEM} ${Taille.PRIM_KEY}: ${taille.id} with value ${taille.elem}")
        tailleRepo.update(taille)
    }

    fun getAllTailles() : Array<Taille> = getAllElems(allTailles)

    fun getAllTaillesString() : ArrayList<String> = getAllElemsString(allTailles)

    fun getTailleStringById(id: Int) : String? = getElemStringById(allTailles, id)

    fun getTailleId(elem: String) : Int = getElemId(allTailles, elem)

    fun getTailleById(id: Int) : Taille {
        getAllTailles().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Taille(0, "0")
    }

    fun getTailleValueForId(id: Int) : String = getTailleById(id).elem

    fun getTailleIdForValue(elem: String) : Int {
        getAllTailles().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

    /**
     * ### PLACELOGO ###
     */
    fun insertPlaceLogo(placeLogo: String) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: insert ${PlaceLogo.ELEM}: $placeLogo")
        placeLogoRepo.insert(placeLogo)
    }

    fun removePlaceLogo(placeLogo: PlaceLogo) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${PlaceLogo.ELEM}: ${placeLogo.elem}")
        placeLogoRepo.remove(placeLogo)
    }

    fun updatePlaceLogo(placeLogo: PlaceLogo) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${PlaceLogo.ELEM} ${PlaceLogo.PRIM_KEY}: ${placeLogo.id} with value ${placeLogo.elem}")
        placeLogoRepo.update(placeLogo)
    }

    fun getAllPlaceLogos() : Array<PlaceLogo> = getAllElems(allPlaceLogos)

    fun getAllPlaceLogosString() : ArrayList<String> = getAllElemsString(allPlaceLogos)

    fun getPlaceLogoStringById(id: Int) : String? = getElemStringById(allPlaceLogos, id)

    fun getPlaceLogoId(elem: String) : Int = getElemId(allPlaceLogos, elem)

    fun getPlaceLogoById(id: Int) : PlaceLogo {
        getAllPlaceLogos().forEach {
            if (it.id == id) {
                return it
            }
        }
        return PlaceLogo(0, "0")
    }

    fun getPlaceLogoValueForId(id: Int) : String = getPlaceLogoById(id).elem

    fun getPlaceLogoIdForValue(elem: String) : Int {
        getAllPlaceLogos().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

    /**
     * ### ARTICLE ###
     */
    fun insertArticle(proId: Int, colId: Int, taiId: Int, plaId: Int) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: insert ${Article.ELEM} ${Article.PRODUIT_ID}: $proId, ${Article.COLORI_ID}: $colId, ${Article.TAILLE_ID}: $taiId, ${Article.PLACELOGO_ID}: $plaId")
        articleRepo.insert(proId, colId, taiId, plaId)
    }

    fun removeArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${Article.ELEM} ${Article.PRIM_KEY}: ${article.id}")
        articleRepo.remove(article)
    }

    fun updateArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${Article.ELEM} ${Article.PRIM_KEY}: ${article.id}")
        articleRepo.update(article)
    }

    fun getAllArticles() : Array<Article> = getAllElems(allArticles)

    fun getAllArticlesString() : ArrayList<String> {
        val res = arrayListOf<String>()
        viewModelScope.launch(Dispatchers.IO) {
            getAllArticles().forEach { res.add(getArticleStringByArticle(it)) }
        }
        return res
    }

    fun getArticleStringByArticle(article: Article) : String {
        val sb = StringBuffer()
        val separ = " / "
        getAllProduits().forEach {
            if (it.id == article.produitId) {
                sb.append(it.elem).append(separ)
                return@forEach
            }
        }
        sb.append(getColoriStringById(article.coloriId)).append(separ)
        /*
        getAllColoris().forEach {
            if (it.id == article.coloriId) {
                sb.append(it.elem).append(separ)
                return@forEach
            }
        }
         */
        getAllTailles().forEach {
            if (it.id == article.tailleId) {
                sb.append(it.elem).append(separ)
                return@forEach
            }
        }
        getAllPlaceLogos().forEach {
            if (it.id == article.placeLogoId) {
                sb.append(it.elem)
                return@forEach
            }
        }
        return sb.toString()
    }

    /*
    fun getArticleStringById(id: Int) : String {
        var res = ""
        viewModelScope.launch(Dispatchers.IO) {
            res = articleRepo.getArticleString(id)
        }
        return res
    }
     */

    fun getArticleById(id: Int) : Article? {
        var res : Article? = null
        viewModelScope.launch(Dispatchers.IO) {
            getAllArticles().forEach {
                if (it.id == id) {
                    d("${Article.ELEM} found for id=$id, $it")
                    res = it
                }
            }
            d("${Article.ELEM} not found for id=$id")
        }
        return res
    }

    suspend fun getArticleByCompIds(proId: Int, colId: Int, taiId: Int, plaId: Int) : Article? {
        var res : Article? = null
        viewModelScope.launch(Dispatchers.IO) {
            res = articleRepo.get(proId, colId, taiId, plaId)
        }
        return res
    }

    /**
     * ### ARTICLESENSTOCK ###
     */
    fun insertArticlesEnStock(articleId: Int, nb: Int, nbAchetes: Int, dateAchat: LocalDate, prixAchat: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            d("$CL: insert ${ArticlesEnStock.ELEM} ${ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID}: $articleId, ${ArticlesEnStock.ARTICLEENSTOCK_NB}: $nb")
            articlesEnStockRepo.insert(articleId, nb, nbAchetes, dateAchat, prixAchat)
        }

    fun removeArticlesEnStock(articlesEnStock: ArticlesEnStock) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${ArticlesEnStock.ELEM} ${ArticlesEnStock.PRIM_KEY}: ${articlesEnStock.id}")
        articlesEnStockRepo.remove(articlesEnStock)
    }

    fun updateArticleEnStock(articlesEnStock: ArticlesEnStock) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${ArticlesEnStock.ELEM} ${ArticlesEnStock.PRIM_KEY}: ${articlesEnStock.id}")
        articlesEnStockRepo.update(articlesEnStock)
    }

    fun getAllArticlesEnStock(): Array<ArticlesEnStock> = getAllElems(allArticlesEnStock)

    fun getArticlesEnStockById(id: Int): ArticlesEnStock {
        getAllArticlesEnStock().forEach {
            if (it.id == id) {
                return it
            }
        }
        return ArticlesEnStock(0, 0, 0, 0, LocalDate.now(), 0.0)
    }

    /**
     * ### TRANSACTIONARTICLE ###
     */
    fun insertTransactionArticle(artId: Int, trTyp: String, nb: Int, prixHT: Double, tva: Double, remise: Double, dat: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: insert ${TransactionArticles.ELEM} ${TransactionArticles.TRANSACTION_ARTICLEID}: $artId, ${TransactionArticles.TRANSACTION_NB}: $nb, ${TransactionArticles.TRANSACTION_PRIXHT}: $prixHT, ${TransactionArticles.TRANSACTION_TVA}: $tva")
        transactionArticleRepo.insert(artId, trTyp, nb, prixHT, tva, remise, dat)
    }

    fun removeTransactionArticle(transactionArticles: TransactionArticles) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: remove ${TransactionArticles.ELEM} ${TransactionArticles.PRIM_KEY}: ${transactionArticles.id}")
        transactionArticleRepo.remove(transactionArticles)
    }

    fun updateTransactionArticle(transactionArticles: TransactionArticles) = viewModelScope.launch(Dispatchers.IO) {
        d("$CL: update ${TransactionArticles.ELEM} ${TransactionArticles.PRIM_KEY}: ${transactionArticles.id}")
        transactionArticleRepo.update(transactionArticles)
    }

    fun getAllTransitionArticles() : Array<TransactionArticles> {
        var res = emptyArray<TransactionArticles>()
        val sb = StringBuffer()
        allTransactionArticles.value?.forEach {
            res = res.plus(it)
            sb.append(it.articleId).append("/").append(it.nb).append("/").append(it.prixHT).append(", ")
        }
        d("getAllTransitionArticles for ${TransactionArticles.ELEM}: size=${res.size}, $sb")
        return res
    }


}
