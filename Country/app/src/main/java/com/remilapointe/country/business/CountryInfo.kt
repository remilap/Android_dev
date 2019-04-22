package com.remilapointe.country.business

import java.util.*

class CountryInfo(val name: InfoWithLanguage,
                  var capital: InfoWithLanguage,
                  var abbrev: String,
                  var surface: InfoWithDate<Long>,
                  var population: InfoWithDate<Long>,
                  var UEEntryIn: Date,
                  var inSchengen: Boolean,
                  var languages: Array<String>,
                  var money: String,
                  var visitedDates: Array<Date>) {

}
