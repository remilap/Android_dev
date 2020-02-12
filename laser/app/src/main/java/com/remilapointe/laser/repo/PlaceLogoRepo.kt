package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.PlaceLogo
import com.remilapointe.laser.dao.PlaceLogoDao

class PlaceLogoRepo(private val dao: PlaceLogoDao) {

    val allPlaceLogos: LiveData<MutableList<PlaceLogo>> = dao.getAll()

    @WorkerThread
    suspend fun insert(elem: String) : Int {
        val placeLogo = PlaceLogo(0, elem)
        d("in Repo insert ${PlaceLogo.ELEM}: ${placeLogo.elem}")
        dao.insert(placeLogo)
        return placeLogo.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val oneObj = allPlaceLogos.value?.get(pos)
        d("in Repo remove ${PlaceLogo.ELEM} id: ${oneObj!!.id}, value: ${oneObj.elem}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    fun remove(placeLogo: PlaceLogo) : Int {
        d("in Repo remove ${PlaceLogo.ELEM} id: ${placeLogo.id}, value: ${placeLogo.elem}")
        return dao.remove(placeLogo.id)
    }

    @WorkerThread
    fun get(key: Int): PlaceLogo {
        d("in Repo get ${PlaceLogo.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(elem: String): PlaceLogo {
        d("in Repo get ${PlaceLogo.ELEM}: $elem")
        return dao.get(elem)
    }

    @WorkerThread
    suspend fun update(placeLogo: PlaceLogo) : Int {
        d("in Repo update ${PlaceLogo.ELEM} id: ${placeLogo.id}, value: ${placeLogo.elem}")
        return dao.update(placeLogo.id, placeLogo.elem)
    }

}
