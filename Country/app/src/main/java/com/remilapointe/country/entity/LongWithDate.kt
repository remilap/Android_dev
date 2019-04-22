package com.remilapointe.country.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "countries")
data class LongWithDate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "date")
    var date: Date = Calendar.getInstance().time,

    @ColumnInfo(name = "value")
    var value: Long = 0

) {
    constructor() : this(0, Calendar.getInstance().time, 0)
}
