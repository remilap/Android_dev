package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.remilapointe.laser.dao.ProduitDao
import com.remilapointe.laser.db.*

class ProduitRepo(private val dao: ProduitDao) {

    val allObjs : LiveData<MutableList<Produit>> = dao.getAll()

    @WorkerThread
    suspend fun insert(coloriId: Int, tailleId: Int, plaeceLogoId: Int) : Int {
        val oneObj = Produit(0, coloriId, tailleId, plaeceLogoId)
        dao.insert(oneObj)
        return oneObj.id
    }

    @WorkerThread
    fun removeAt(pos: Int) {
        val oneObj = allObjs.value?.get(pos)
        dao.remove(oneObj!!.id)
    }

    @WorkerThread
    fun remove(id: Int) {
        //val oneObj = allObjs.value?.get(pos)
        dao.remove(id)
    }

    @WorkerThread
    fun get(id: Int): Produit {
        return dao.get(id)
    }

}
