package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.dao.ProduitDao
import com.remilapointe.laser.db.Produit

class ProduitRepo(private val dao: ProduitDao) {

    val allProduits: LiveData<MutableList<Produit>> = dao.getAll()

    @WorkerThread
    suspend fun insert(elem: String) : Int {
        val oneObj = Produit(0, elem)
        d("in Repo insert ${Produit.ELEM} id: ${oneObj.elem}")
        dao.insert(oneObj)
        return oneObj.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val oneObj = allProduits.value?.get(pos)
        d("in Repo remove ${Produit.ELEM} id: ${oneObj!!.id}, value: ${oneObj.elem}")
        dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(produit: Produit) {
        d("in Repo remove ${Produit.ELEM} id: ${produit.id}, value: ${produit.elem}")
        dao.remove(produit.id)
    }

    @WorkerThread
    fun get(key: Int): Produit {
        d("in Repo get ${Produit.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(elem: String): Produit {
        d("in Repo get ${Produit.ELEM}: $elem")
        return dao.get(elem)
    }

    @WorkerThread
    suspend fun update(produit: Produit) {
        d("in Repo update ${Produit.ELEM} id: ${produit.id}, value: ${produit.elem}")
        dao.update(produit.id, produit.elem)
    }

}
