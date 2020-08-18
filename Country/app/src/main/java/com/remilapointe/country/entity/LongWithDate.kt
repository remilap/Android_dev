package eu.remilapointe.country.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = LongWithDate.TABLE_NAME
)
data class LongWithDate(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(index = true)
    var info: Int,
    @ColumnInfo(index = true)
    var country_id: Long,
    var date: LocalDate = LocalDate.now(),
    var value: Long = 0
) {
    companion object {
        const val ELEM = "longWithDate"
        const val TABLE_NAME = "${Country.ELEM}_$ELEM"
        const val PRIM_KEY = "id"
        const val LONG_INFO = "info"
        const val LONG_COUNTRY_ID = "country_id"
        const val LONG_DATE = "date"
        const val LONG_VALUE = "value"
        const val LONG_INFO_SURFACE = 1
        const val LONG_INFO_POPULATION = 2
    }

}
