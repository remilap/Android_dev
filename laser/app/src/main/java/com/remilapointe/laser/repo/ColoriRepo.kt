package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.Colori
import com.remilapointe.laser.dao.ColoriDao

class ColoriRepo(private val dao: ColoriDao) {

    val allObjs: LiveData<MutableList<Colori>> = dao.getAll()

    @WorkerThread
    suspend fun insert(elem: String) {
        val oneObj = Colori(0, elem)
        d("in Repo insert coloriId: ${oneObj.elem}")
        dao.insert(oneObj)
    }

    @WorkerThread
    suspend fun remove(colori: Colori) {
        d("in Repo remove coloriId: ${colori.elem}")
        dao.remove(colori.elem)
    }

    @WorkerThread
    fun get(elem: String): Colori {
        d("in Repo get coloriId: $elem")
        return dao.get(elem)
    }

}
