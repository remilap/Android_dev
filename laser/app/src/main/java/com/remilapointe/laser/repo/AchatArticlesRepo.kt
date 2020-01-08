package com.remilapointe.laser.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import com.remilapointe.laser.dao.AchatArticlesDao
import com.remilapointe.laser.db.AchatArticles
import java.util.*

class AchatArticlesRepo(private val dao: AchatArticlesDao) {

    val allAchatArticles : LiveData<MutableList<AchatArticles>> = dao.getAll()

    @WorkerThread
    suspend fun insert(artId: Int, nb: Int, prixAchatHt: Double, date: Date) {
        val oneObj = AchatArticles(0, artId, nb, prixAchatHt, date)
        d("in Repo insert ${AchatArticles.ELEM} id: ${oneObj.id}")
        dao.insert(oneObj)
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val oneObj = allAchatArticles.value?.get(pos)
        d("in Repo remove ${AchatArticles.ELEM} id: ${oneObj!!.id}, nb: ${oneObj.nb}")
        dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(achatArticles: AchatArticles) {
        d("in Repo remove ${AchatArticles.ELEM} id: ${achatArticles.id}, nb: ${achatArticles.nb}")
        dao.remove(achatArticles.id)
    }

    @WorkerThread
    fun get(key: Int) : AchatArticles{
        return dao.get(key)
    }

    @WorkerThread
    suspend fun update(achatArticles: AchatArticles) {
        d("in Repo update ${AchatArticles.ELEM} id: ${achatArticles.id}, nb: ${achatArticles.nb}")
        dao.update(achatArticles.id, achatArticles.articleId, achatArticles.nb, achatArticles.prixAchatHT, achatArticles.date)
    }

}
