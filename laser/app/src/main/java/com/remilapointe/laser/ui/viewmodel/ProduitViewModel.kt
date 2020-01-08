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

class ProduitViewModel(application: Application) : AndroidViewModel(application) {

    private val produitRepo: ProduitRepo

    val allProduits: LiveData<MutableList<Produit>>

    init {
        d("ProduitViewModel:init")
        val produitDao = LaserRoomDatabase.getDatabase(application).produitDao()
        produitRepo = ProduitRepo(produitDao)
        allProduits = produitRepo.allProduits
        if (allProduits.value == null) {
            d("ProduitViewModel:getAllProduits, size: 0")
        } else {
            d("ProduitViewModel:getAllProduits, size: " + allProduits.value?.size)
        }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(produit: String) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert ${Produit.ELEM}: $produit")
        produitRepo.insert(produit)
    }

    fun remove(produit: Produit) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get ${Produit.ELEM}: ${produit.elem}")
        produitRepo.remove(produit)
    }

    fun update(produit: Produit) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model update ${Produit.ELEM} ${Produit.PRIM_KEY}: ${produit.id} with value ${produit.elem}")
        produitRepo.update(produit)
    }

    fun getAllProduits() : Array<Produit> =
        Array(size = allProduits.value?.size!!) { i -> allProduits.value?.get(i)!! }

    fun getProduitById(id: Int) : Produit {
        getAllProduits().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Produit(0, "0")
    }

    fun getValueForId(id: Int) : String = getProduitById(id).elem

    fun getIdForValue(elem: String) : Int {
        getAllProduits().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

}
