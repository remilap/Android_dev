package eu.remilapointe.country.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import eu.remilapointe.country.db.CountryDb
import eu.remilapointe.country.entity.Country
import eu.remilapointe.country.entity.LongWithDate
import eu.remilapointe.country.entity.StringWithDate
import eu.remilapointe.country.entity.StringWithLanguage
import eu.remilapointe.country.repository.CountryRepo
import eu.remilapointe.country.repository.LongWithDateRepo
import eu.remilapointe.country.repository.StringWithDateRepo
import eu.remilapointe.country.repository.StringWithLanguageRepo
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CountryViewModel(application: Application) : AndroidViewModel(application) {

    private val countryRepo: CountryRepo
    private val longWithDateRepo: LongWithDateRepo
    private val stringWithDateRepo: StringWithDateRepo
    private val stringWithLanguageRepo: StringWithLanguageRepo

    val allCountries: LiveData<MutableList<Country>>
    val allLongWithDates: LiveData<MutableList<LongWithDate>>
    val allStringWithDates: LiveData<MutableList<StringWithDate>>
    val allStringWithLanguages: LiveData<MutableList<StringWithLanguage>>

    private val CL = this::class.java.simpleName

    init {
        logger.debug("$CL:init")
        val countryDao = CountryDb.getDatabase(application).countryDao()
        countryRepo = CountryRepo(countryDao)
        allCountries = countryRepo.allCountries

        val longWithDateDao = CountryDb.getDatabase(application).longWithDateDao()
        longWithDateRepo = LongWithDateRepo(longWithDateDao)
        allLongWithDates = longWithDateRepo.allLongWithDates

        val stringWithDateDao = CountryDb.getDatabase(application).stringWithDateDao()
        stringWithDateRepo = StringWithDateRepo(stringWithDateDao)
        allStringWithDates = stringWithDateRepo.allStringWithDates

        val stringWithLanguageDao = CountryDb.getDatabase(application).stringWithLanguageDao()
        stringWithLanguageRepo = StringWithLanguageRepo(stringWithLanguageDao)
        allStringWithLanguages = stringWithLanguageRepo.allStringWithLanguages
    }
}
