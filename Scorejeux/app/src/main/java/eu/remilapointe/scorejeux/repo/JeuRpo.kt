package eu.remilapointe.scorejeux.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import eu.remilapointe.scorejeux.dao.JeuDao
import eu.remilapointe.scorejeux.entity.Jeu

class JeuRpo(private val dao: JeuDao) {

    val allJeus: LiveData<MutableList<Jeu>> = dao.getAll()

    @WorkerThread
    suspend fun insert(name: String): Long {
        val obj = Jeu(0, name)
        d("in Repo insert ${Jeu.ELEM} id: ${obj.name}")
        dao.insert(obj)
        return obj.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int): Int {
        val oneObj = allJeus.value?.get(pos)
        d("in Repo remove ${Jeu.ELEM} id: ${oneObj!!.id}, value: ${oneObj.name}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(obj: Jeu): Int {
        d("in Repo remove ${Jeu.ELEM} id: ${obj.id}, value: ${obj.name}")
        return dao.remove(obj.id)
    }

    @WorkerThread
    fun get(key: Long): Jeu {
        d("in Repo get ${Jeu.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(name: String): Jeu {
        d("in Repo get ${Jeu.ELEM}: $name")
        return dao.get(name)
    }

    @WorkerThread
    suspend fun update(obj: Jeu): Int {
        d("in Repo update ${Jeu.ELEM} id: ${obj.id}, value ${obj.name}")
        return dao.update(obj.id, obj.name)
    }
}
