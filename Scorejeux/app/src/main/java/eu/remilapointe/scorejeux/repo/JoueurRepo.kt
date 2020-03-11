package eu.remilapointe.scorejeux.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import eu.remilapointe.scorejeux.dao.JoueurDao
import eu.remilapointe.scorejeux.entity.Joueur

class JoueurRepo(private val dao: JoueurDao) {

    val allJoueurs: LiveData<MutableList<Joueur>> = dao.getAll()

    @WorkerThread
    suspend fun insert(name: String): Long {
        val obj = Joueur(0, name)
        d("in Repo insert ${Joueur.ELEM} id: ${obj.name}")
        dao.insert(obj)
        return obj.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int): Int {
        val oneObj = allJoueurs.value?.get(pos)
        d("in Repo remove ${Joueur.ELEM} id: ${oneObj!!.id}, value: ${oneObj.name}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(obj: Joueur): Int {
        d("in Repo remove ${Joueur.ELEM} id: ${obj.id}, value: ${obj.name}")
        return dao.remove(obj.id)
    }

    @WorkerThread
    fun get(key: Long): Joueur {
        d("in Repo get ${Joueur.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun get(name: String): Joueur {
        d("in Repo get ${Joueur.ELEM}: $name")
        return dao.get(name)
    }

    @WorkerThread
    suspend fun update(obj: Joueur): Int {
        d("in Repo update ${Joueur.ELEM} id: ${obj.id}, value ${obj.name}")
        return dao.update(obj.id, obj.name)
    }
}
