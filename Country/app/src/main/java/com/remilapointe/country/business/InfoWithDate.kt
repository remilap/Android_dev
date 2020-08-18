package eu.remilapointe.country.business

import java.time.LocalDate

class InfoWithDate<T> {

    private var lst : HashMap<LocalDate, T> = hashMapOf()

    fun addInfo(date: LocalDate, info: T) {
        if (! lst.containsKey(date)) {
            lst[date] = info
        }
    }

    fun getInfoForDate(date: LocalDate) : T? {
        if (lst.containsKey(date)) {
            return lst[date]
        }
        return null
    }

    fun getSortedDates() : List<LocalDate> {
        return lst.keys.sorted()
    }

    fun getMostRecentDate() : LocalDate? {
        return getSortedDates()[0]
    }

    fun getMostRecentInfo() : T? {
        val dat = getMostRecentDate() ?: return null
        return getInfoForDate(dat)
    }

}
