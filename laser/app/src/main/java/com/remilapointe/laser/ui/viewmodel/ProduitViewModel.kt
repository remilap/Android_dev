package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.*
import com.remilapointe.laser.repo.ColoriRepo
import com.remilapointe.laser.repo.PlaceLogoRepo
import com.remilapointe.laser.repo.ProduitRepo
import com.remilapointe.laser.repo.TailleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProduitViewModel(application: Application) : AndroidViewModel(application) {

    private val produitRepo: ProduitRepo
    private val coloriRepo: ColoriRepo
    private val tailleRepo: TailleRepo
    private val placeLogoRepo: PlaceLogoRepo

    val allProduits: LiveData<MutableList<Produit>>
    val allColoris: LiveData<MutableList<Colori>>
    val allTailles: LiveData<MutableList<Taille>>
    val allPlaceLogs: LiveData<MutableList<PlaceLogo>>

    init {
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
    fun insert(coloriId: Int, tailleId: Int, placeLogoId: Int) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert produit: $coloriId/$tailleId/$placeLogoId")
        produitRepo.insert(coloriId, tailleId, placeLogoId)
    }

    fun remove(produit: Produit) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get produit: ${produit.id}")
        produitRepo.remove(produit.id)
    }

    fun getAllObjs() : Array<Produit> {
        return Array(size = allProduits.value?.size!!) { i -> allProduits.value?.get(i)!! }
    }

    fun getProduitById(id: Int) : Produit? {
        getAllObjs().forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    fun getColoriById(id: Int) : String? = allColoris.value!![id].elem

    fun getTailleById(id: Int) : String? = allTailles.value!![id].elem

    fun getPlaceLogoById(id: Int) : String? = allPlaceLogs.value!![id].elem

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
