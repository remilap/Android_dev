package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.db.Taille
import com.remilapointe.laser.dao.TailleDao

class TailleRepo(private val dao: TailleDao) {

    val allTailles: LiveData<MutableList<Taille>> = dao.getAll()

    @WorkerThread
    suspend fun insert(elem: String) {
        val oneObj = Taille(0, elem)
        d("in Repo insert ${Taille.ELEM}: ${oneObj.elem}")
        dao.insert(oneObj)
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val oneObj = allTailles.value?.get(pos)
        d("in Repo remove ${Taille.ELEM} id: ${oneObj!!.id}, value: ${oneObj.elem}")
        dao.remove(oneObj.id)
    }

    @WorkerThread
    fun remove(taille: Taille) {
        d("in Repo remove ${Taille.ELEM} id: ${taille.id}, value: ${taille.elem}")
        dao.remove(taille.id)
    }

    @WorkerThread
    fun get(key: Int): Taille {
        d("in Repo get ${Taille.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(elem: String): Taille {
        d("in Repo get ${Taille.ELEM}: $elem")
        return dao.get(elem)
    }

    @WorkerThread
    suspend fun update(taille: Taille) {
        d("in Repo update ${Taille.ELEM} id: ${taille.id}, value: ${taille.elem}")
        dao.update(taille.id, taille.elem)
    }

}
