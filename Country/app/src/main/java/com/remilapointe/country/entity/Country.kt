package com.remilapointe.country.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "name_fr")
    var name_fr: String = "France",

    @ColumnInfo(name = "name_en")
    var name_en: String = "France",

    @ColumnInfo(name = "capital_fr")
    var capitale_fr: String = "Paris",

    @ColumnInfo(name = "capital_en")
    var capitale_en: String = "Paris",

    @ColumnInfo(name = "abbrev")
    var abbrev: String = "FR",

    @ColumnInfo(name = "surface")   // in km2
    var surface: Long = 632834,

    @ColumnInfo(name = "population")
    var population: Long = 67200000,

    @ColumnInfo(name = "year_reference")    // year reference for population
    var year_reference: Int = 2018,

    @ColumnInfo(name = "ue_entry_in")
    var ue_entry_in: Int = 1957,

    @ColumnInfo(name = "money")
    var money: String = "Euro",

    @ColumnInfo(name = "in_schengen")
    var in_schengen: Boolean = true,

    @ColumnInfo(name = "langage")
    var langage: String = "Français",

    @ColumnInfo(name = "visited_in")
    var visited_in: String = "2018"

) : Serializable {

    constructor(): this(0, "", "", "", "", "", 0, 0, 0, 0, "", false, "", "")

    override fun toString(): String {
        return id.toString() +
                '|' + name_en +
                '|' + name_fr +
                '|' + capitale_en +
                '|' + capitale_fr +
                '|' + abbrev
    }

}
