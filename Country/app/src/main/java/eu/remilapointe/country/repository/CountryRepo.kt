package eu.remilapointe.country.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import eu.remilapointe.country.dao.CountryDao
import eu.remilapointe.country.entity.Country
import mu.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

class CountryRepo(private val dao: CountryDao) {

    val allCountries: LiveData<MutableList<Country>> = dao.getAll()

    @WorkerThread
    suspend fun insert(
        nameId: Long,
        capitaleId: Long,
        abbrev: String,
        surfaceId: Long,
        populationId: Long,
        ueEntry: LocalDate,
        ueExit: LocalDate,
        inSchengen: Boolean,
        languages: String,
        moneyId: Long,
        visitedIn: String) : Long {
        val elem = Country(
            0,
            nameId,
            capitaleId,
            abbrev,
            surfaceId,
            populationId,
            ueEntry,
            ueExit,
            inSchengen,
            languages,
            moneyId,
            visitedIn)
        logger.debug("in Repo insert ${Country.ELEM} id: ${elem.id}")
        dao.insert(elem)
        return elem.id
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) : Int {
        val oneObj = dao.getAll().value?.get(pos)
        logger.debug("in Repo remove ${Country.ELEM} id: ${oneObj!!.id}, value: ${oneObj.abbrev}")
        return dao.remove(oneObj.id)
    }

    @WorkerThread
    suspend fun remove(country: Country) : Int {
        logger.debug("in Repo remove ${Country.ELEM} id: ${country.id}, value: ${country.abbrev}")
        return dao.remove(country.id)
    }

    @WorkerThread
    fun get(key: Long): Country? {
        logger.debug("in Repo get ${Country.ELEM} id: $key")
        return dao.get(key)
    }

}
