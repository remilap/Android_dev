package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.remilapointe.laser.dao.ColoriDao
import com.remilapointe.laser.dao.ProduitDao
import com.remilapointe.laser.db.*

class ProduitRepo(private val produitDao: ProduitDao) {

    val allProduits : LiveData<MutableList<Produit>> = produitDao.getAll()

    @WorkerThread
    suspend fun insert(coloriId: Int, tailleId: Int, plaeceLogoId: Int) : Int {
        val oneObj = Produit(0, coloriId, tailleId, plaeceLogoId)
        produitDao.insert(oneObj)
        return oneObj.id
    }

    @WorkerThread
    fun removeAt(pos: Int) {
        val oneObj = allProduits.value?.get(pos)
        produitDao.remove(oneObj!!.id)
    }

    @WorkerThread
    fun remove(id: Int) {
        //val oneObj = allProduits.value?.get(pos)
        produitDao.remove(id)
    }

    @WorkerThread
    fun get(id: Int): Produit {
        return produitDao.get(id)
    }

}
