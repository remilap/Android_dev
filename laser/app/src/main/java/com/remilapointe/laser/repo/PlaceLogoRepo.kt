package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.PlaceLogo
import com.remilapointe.laser.dao.PlaceLogoDao

class PlaceLogoRepo(private val dao: PlaceLogoDao) {

    val allPlaceLogos: LiveData<MutableList<PlaceLogo>> = dao.getAll()

    @WorkerThread
    suspend fun insert(elem: String) {
        val oneObj = PlaceLogo(0, elem)
        d("in Repo insert placelogo: ${oneObj.elem}")
        dao.insert(oneObj)
    }

    @WorkerThread
    fun remove(placeLogo: PlaceLogo) {
        d("in Repo remove placelogo: ${placeLogo.elem}")
        dao.remove(placeLogo.elem)
    }

    @WorkerThread
    fun get(elem: String): PlaceLogo {
        d("in Repo get placelogo: $elem")
        return dao.get(elem)
    }

}
