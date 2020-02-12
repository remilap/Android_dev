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
        val produit = Produit(0, elem)
        d("in Repo insert ${Produit.ELEM} id: ${produit.elem}")
        dao.insert(produit)
        return produit.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val oneObj = allProduits.value?.get(pos)
        d("in Repo remove ${Produit.ELEM} id: ${oneObj!!.id}, value: ${oneObj.elem}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(produit: Produit) : Int {
        d("in Repo remove ${Produit.ELEM} id: ${produit.id}, value: ${produit.elem}")
        return dao.remove(produit.id)
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
