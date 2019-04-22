package com.remilapointe.country

import com.remilapointe.country.business.InfoWithDate
import org.junit.Before
import org.junit.Test
import java.util.*

class InfoWithDateShould {

    private lateinit var population: InfoWithDate<Long>
    private lateinit var dates: Array<Date>
    private lateinit var pops: LongArray

    @Before
    fun setup() {
        population = InfoWithDate()
    }

    @Test
    fun returnEmptyIfNoValue() {
        val res = population.getMostRecentInfo()
        assert(res == null)
    }

    private fun setOneDate(y: Int, m: Int, d: Int): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, y)
        cal.set(Calendar.MONTH, m)
        cal.set(Calendar.DAY_OF_MONTH, d)
        return cal.time
    }

    private fun addSeveralEntries(entries: IntArray) {
        var i = 0
        var p = 0
        while (i < entries.size) {
            dates[p] = setOneDate(entries[i++], entries[i++], entries[i++])
            pops[p] = entries[i++].toLong()
            population.addInfo(dates[p], pops[p])
            p++
        }
    }

    @Test
    fun returnOneInfoDate() {
        addSeveralEntries(intArrayOf(2018, Calendar.JULY, 1, 1000))
        val res = population.getMostRecentDate()
        assert(res == dates[0])
        val pop = population.getMostRecentInfo()
        assert(pop == pops[0])
    }

    @Test
    fun returnMostRecentInfoDate() {
        addSeveralEntries(intArrayOf(2018, Calendar.JULY, 1, 1000, 2018, Calendar.SEPTEMBER, 1, 2000, 2018, Calendar.JANUARY, 1, 1500))
        val res = population.getMostRecentDate()
        assert(res == dates[1])
        val pop = population.getMostRecentInfo()
        assert(pop == pops[1])
    }

    @Test
    fun returnAllDates() {
        addSeveralEntries(intArrayOf(2018, Calendar.JULY, 1, 1000, 2018, Calendar.SEPTEMBER, 1, 2000, 2018, Calendar.JANUARY, 1, 1500))
        val res = population.getSortedDates()
        assert(res[0] == dates[2])
        assert(res[1] == dates[0])
        assert(res[2] == dates[1])
    }

    @Test
    fun returnValueForDate() {
        addSeveralEntries(intArrayOf(2018, Calendar.JULY, 1, 1000, 2018, Calendar.SEPTEMBER, 1, 2000, 2018, Calendar.JANUARY, 1, 1500))
        for (i in 0..2)
            assert(population.getInfoForDate(dates[i]) == pops[i])
    }

}