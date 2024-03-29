package eu.remilapointe.country.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import eu.remilapointe.country.dao.LongWithDateDao
import eu.remilapointe.country.entity.LongWithDate
import mu.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

class LongWithDateRepo(private val dao: LongWithDateDao) {

    val allLongWithDates: LiveData<MutableList<LongWithDate>> = dao.getAll()

    @WorkerThread
    suspend fun insert(info: Int, countryId: Long, date: LocalDate, value: Long) : Long {
        val elem = LongWithDate(0, info, countryId, date, value)
        logger.debug("in Repo insert ${LongWithDate.ELEM} id: ${elem.id}")
        dao.insert(elem)
        return elem.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val oneObj = dao.getAll().value?.get(pos)
        logger.debug("in Repo remove ${LongWithDate.ELEM} id: ${oneObj!!.id}, value: ${oneObj.value}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(longWithDate: LongWithDate) : Int {
        logger.debug("in Repo remove ${LongWithDate.ELEM} id: ${longWithDate.id}, value: ${longWithDate.value}")
        return dao.remove(longWithDate.id)
    }

    @WorkerThread
    fun get(key: Long): LongWithDate? {
        logger.debug("in Repo get ${LongWithDate.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun getLongInfoForCountry(info: Int, countryId: Long): LiveData<MutableList<LongWithDate>> = dao.getLongInfoForCountry(info, countryId)

}
