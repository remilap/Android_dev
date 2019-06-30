package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.Colori
import com.remilapointe.laser.db.LaserRoomDatabase
import com.remilapointe.laser.db.Produit
import com.remilapointe.laser.repo.ColoriRepo
import com.remilapointe.laser.repo.ProduitRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProduitViewModel(application: Application) : AndroidViewModel(application) {

    private val myRepo: ProduitRepo

    val allObjs: LiveData<MutableList<Produit>>

    init {
        val myDao = LaserRoomDatabase.getDatabase(application, viewModelScope).produitDao()
        myRepo = ProduitRepo(myDao)
        allObjs = myRepo.allObjs
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(coloriId: Int, tailleId: Int, placeLogoId: Int) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert produit: $coloriId/$tailleId/$placeLogoId")
        myRepo.insert(coloriId, tailleId, placeLogoId)
    }

    fun remove(produit: Produit) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get produit: ${produit.id}")
        myRepo.remove(produit.id)
    }

    fun getAllObjs() : Array<Produit> {
        return Array(size = allObjs.value?.size!!) { i -> allObjs.value?.get(i)!! }
    }

    fun getProduitById(id: Int) : Produit? {
        getAllObjs().forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

}
