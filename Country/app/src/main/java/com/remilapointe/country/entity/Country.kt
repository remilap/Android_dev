package eu.remilapointe.country.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = Country.TABLE_NAME
)
data class Country(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,
    var nameId: Long, // id of StringWithLanguage
    var capitaleId: Long, // id of StringWithLanguage
    var abbrev: String,
    var surfaceId: Long, // id of LongWithDate
    var populationId: Long, // id of LongWithDate
    var UEEntryIn: LocalDate?,
    var UEExitIn: LocalDate?,
    var inSchengen: Boolean = false,
    var languages: String, // different languages comma separated
    var money: Long, // id of StringWithDate
    var visitedIn: String // different dates comma separated
    ) {
    companion object {
        const val ELEM = "country"
        const val TABLE_NAME = "country_$ELEM"
        const val PRIM_KEY = "id"
        const val NAMEID = "nameId"
        const val CAPITALEID = "capitaleId"
        const val ABBREV = "abbrev"
        const val SURFACEID = "surfaceId"
        const val POPULATIONID = "populationId"
        const val UEENTRYIN = "UEEntryIn"
        const val UEEXITIN = "UEExitIn"
        const val INSCHENGEN = "inSchengen"
        const val LANGUAGES = "languages"
        const val MONEYID = "moneyId"
        const val VISITEDIN = "visitedIn"
    }
}
