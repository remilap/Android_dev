package eu.remilapointe.country.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.log4k.d
import eu.remilapointe.country.dao.StringWithLanguageDao
import eu.remilapointe.country.entity.StringWithLanguage

class StringWithLanguageRepo(private val dao: StringWithLanguageDao) {

    val allStringWithLanguages: LiveData<MutableList<StringWithLanguage>> = dao.getAll()

    @WorkerThread
    suspend fun insert(info: Int, countryId: Long, language: String, value: String): Long {
        val obj = StringWithLanguage(0, info, countryId, language, value)
        d("in Repo insert ${StringWithLanguage.ELEM} id: ${obj.value}")
        dao.insert(obj)
        return obj.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int): Int {
        val oneObj = allStringWithLanguages.value?.get(pos)
        d("in Repo remove ${StringWithLanguage.ELEM} id: ${oneObj!!.id}, value: ${oneObj.value}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(obj: StringWithLanguage): Int {
        d("in Repo remove ${StringWithLanguage.ELEM} id: ${obj.id}, value: ${obj.value}")
        return dao.remove(obj.id)
    }

    @WorkerThread
    fun get(key: Long): StringWithLanguage {
        d("in Repo get ${StringWithLanguage.ELEM} id: $key")
        return dao.get(key)
    }

    @WorkerThread
    fun getStringInfoForCountry(info: Int, countryId: Long): LiveData<MutableList<StringWithLanguage>> = dao.getStringInfoForCountry(info, countryId)

    @WorkerThread
    suspend fun update(obj: StringWithLanguage): Int {
        d("in Repo update ${StringWithLanguage.ELEM} id: ${obj.id}, value ${obj.value}")
        return dao.update(obj.id, obj.info, obj.country_id, obj.language, obj.value)
    }

}
