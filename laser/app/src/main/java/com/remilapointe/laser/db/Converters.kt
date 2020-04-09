package com.remilapointe.laser.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

}
