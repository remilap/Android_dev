package com.remilapointe.country.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.remilapointe.country.db.LongWithDateDb
import java.util.*

@Entity(tableName = LongWithDateDb.DB_NAME)
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
