package com.remilapointe.country

import com.remilapointe.country.business.InfoWithLanguage
import org.junit.Before
import org.junit.Test

class InfoWithLanguageShould {

    private lateinit var countryName: InfoWithLanguage

    @Before
    fun setUp() {
        countryName = InfoWithLanguage()
    }

    @Test
    fun returnEmptyIfNoValue() {
        val res = countryName.getInfoForLang("fr")
        assert(res.isEmpty())
    }

    @Test
    fun returnOneValueIfOneValue() {
        countryName.addInfo("fr", "chaise")
        val res = countryName.getInfoForLang("fr")
        assert(res == "chaise")
    }

    @Test
    fun returnGoodValueIfSeveralValues() {
        countryName.addInfo("fr", "chaise")
        countryName.addInfo("en", "chair")
        countryName.addInfo("sp", "silla")
        var res = countryName.getInfoForLang("fr")
        assert(res == "chaise")
        res = countryName.getInfoForLang("en")
        assert(res == "chair")
        res = countryName.getInfoForLang("sp")
        assert(res == "silla")
    }

    @Test
    fun returnEmptyIfUnknownLang() {
        countryName.addInfo("da", "toto")
        val res = countryName.getInfoForLang("da")
        assert(res.isEmpty())
    }

}
