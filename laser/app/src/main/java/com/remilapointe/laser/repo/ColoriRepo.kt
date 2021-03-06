package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.Colori
import com.remilapointe.laser.dao.ColoriDao

class ColoriRepo(private val dao: ColoriDao) {

    val allColoris: LiveData<MutableList<Colori>> = dao.getAll()

    @WorkerThread
    suspend fun insert(elem: String) : Int {
        val colori = Colori(0, elem)
        d("in Repo insert ${Colori.ELEM} id: ${colori.elem}")
        dao.insert(colori)
        return colori.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val oneObj = allColoris.value?.get(pos)
        d("in Repo remove ${Colori.ELEM} id: ${oneObj!!.id}, value: ${oneObj.elem}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(colori: Colori) : Int {
        d("in Repo remove ${Colori.ELEM} id: ${colori.id}, value: ${colori.elem}")
        return dao.remove(colori.id)
    }

    @WorkerThread
    fun get(key: Int): Colori {
        d("in Repo get ${Colori.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(elem: String): Colori {
        d("in Repo get ${Colori.ELEM}: $elem")
        return dao.get(elem)
    }

    @WorkerThread
    suspend fun update(colori: Colori) : Int {
        d("in Repo update ${Colori.ELEM} id: ${colori.id}, value ${colori.elem}")
        return dao.update(colori.id, colori.elem)
    }
}
