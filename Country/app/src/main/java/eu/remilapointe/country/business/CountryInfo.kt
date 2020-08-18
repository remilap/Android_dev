package eu.remilapointe.country.business

import java.time.LocalDate

data class CountryInfo(
    val name: InfoWithLanguage<String>,
    var capital: InfoWithLanguage<String>,
    var abbrev: String,
    var surface: InfoWithDate<Long>,
    var population: InfoWithDate<Long>,
    var UEEntryIn: LocalDate,
    var inSchengen: Boolean = false,
    var languages: ArrayList<String>,
    var money: String,
    var visitedDates: ArrayList<LocalDate>
) {

}
