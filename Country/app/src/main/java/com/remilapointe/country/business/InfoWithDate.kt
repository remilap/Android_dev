package com.remilapointe.country.business

import java.util.*

class InfoWithDate<T> {

    private var lst : HashMap<Date, T> = hashMapOf()

    fun addInfo(date: Date, info: T) {
        if (! lst.containsKey(date)) {
            lst[date] = info
        }
    }

    fun getInfoForDate(date: Date) : T? {
        if (lst.containsKey(date)) {
            return lst[date]!!
        }
        return null
    }

    fun getSortedDates() : List<Date> {
        return lst.keys.sorted()
    }

    fun getMostRecentDate() : Date? {
        return getSortedDates()[0]
    }

    fun getMostRecentInfo() : T? {
        val dat = getMostRecentDate() ?: return null
        return getInfoForDate(dat)
    }

}
