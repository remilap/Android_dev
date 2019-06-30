package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.remilapointe.laser.db.QuantiteProduit
import com.remilapointe.laser.dao.QuantiteProduitDao

class QuantiteProduitRepo(private val dao: QuantiteProduitDao) {

    val allObjs: LiveData<MutableList<QuantiteProduit>> = dao.getAll()

    @WorkerThread
    suspend fun insert(reference: String, idProduit: Int, nb: Int) {
        val oneObj = QuantiteProduit(reference, idProduit, nb)
        dao.insert(oneObj)
    }

    @WorkerThread
    fun removeAt(pos: Int) {
        val oneObj = allObjs.value?.get(pos)
        dao.remove(oneObj!!.reference)
    }

    @WorkerThread
    fun get(ref: String) : QuantiteProduit{
        return dao.get(ref)
    }

}
