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
        d("in Repo insert elem: ${oneObj.elem}")
        dao.insert(oneObj)
    }

    @WorkerThread
    fun remove(taille: Taille) {
        d("in Repo remove taille: ${taille.elem}")
        dao.remove(taille.elem)
    }

    @WorkerThread
    fun get(elem: String): Taille {
        d("in Repo get taille: $elem")
        return dao.get(elem)
    }

}
