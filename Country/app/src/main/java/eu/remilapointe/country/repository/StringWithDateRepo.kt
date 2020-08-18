package eu.remilapointe.country.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import eu.remilapointe.country.dao.StringWithDateDao
import eu.remilapointe.country.entity.StringWithDate
import java.time.LocalDate

class StringWithDateRepo(private val dao: StringWithDateDao) {

    val allStringWithDates: LiveData<MutableList<StringWithDate>> = dao.getAll()

    @WorkerThread
    suspend fun insert(info: Int, countryId: Long, date: LocalDate, value: String) : Long {
        val elem = StringWithDate(0, info, countryId, date, value)
        d("in Repo insert ${StringWithDate.ELEM} id: ${elem.id}")
        dao.insert(elem)
        return elem.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val oneObj = dao.getAll().value?.get(pos)
        d("in Repo remove ${StringWithDate.ELEM} id: ${oneObj!!.id}, value: ${oneObj.value}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(stringWithDate: StringWithDate) : Int {
        d("in Repo remove ${StringWithDate.ELEM} id: ${stringWithDate.id}, value: ${stringWithDate.value}")
        return dao.remove(stringWithDate.id)
    }

    @WorkerThread
    fun get(key: Long): StringWithDate? {
        d("in Repo get ${StringWithDate.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun getStringInfoForCountry(info: Int, countryId: Long): LiveData<MutableList<StringWithDate>> = dao.getStringInfoForCountry(info, countryId)

    @WorkerThread
    suspend fun update(obj: StringWithDate): Int {
        d("in Repo update ${StringWithDate.ELEM} id: ${obj.id}, value ${obj.value}")
        return dao.update(obj.id, obj.info, obj.country_id, obj.date, obj.value)
    }

}
