package eu.remilapointe.country.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = StringWithLanguage.TABLE_NAME
)
data class StringWithLanguage(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(index = true)
    var info: Int,
    @ColumnInfo(index = true)
    var country_id: Long,
    var language: String,
    var value: String = ""
) {
    companion object {
        const val ELEM = "StringWithLanguage"
        const val TABLE_NAME = "${Country.ELEM}_$ELEM"
        const val PRIM_KEY = "id"
        const val STRING_INFO = "info"
        const val STRING_COUNTRY_ID = "country_id"
        const val STRING_LANGUAGE = "language"
        const val STRING_VALUE = "value"
        const val STRING_INFO_NAME = 1
        const val STRING_INFO_CAPITALE = 2
        const val STRING_INFO_MONEY = 3
    }
}
