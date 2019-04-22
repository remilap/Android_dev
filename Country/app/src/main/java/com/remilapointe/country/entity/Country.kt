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

    @ColumnInfo(name = "name_id")
    var name_id: Long = 0,

    @ColumnInfo(name = "capital_id")
    var capitale_id: Long = 0,

    @ColumnInfo(name = "abbrev")
    var abbrev: String = "FR",

    @ColumnInfo(name = "surface_id")
    var surface_id: Long = 0,

    @ColumnInfo(name = "population_id")
    var population_id: Long = 0,

    @ColumnInfo(name = "ue_entry_in")
    var ue_entry_in: Int = 0,

    @ColumnInfo(name = "money")
    var money: String = "Euro",

    @ColumnInfo(name = "in_schengen")
    var in_schengen: Boolean = true,

    @ColumnInfo(name = "langage")
    var langage: String = "Français",

    @ColumnInfo(name = "visited_in")
    var visited_in: String = "2018"

) : Serializable {

    constructor(): this(0, 0, 0, "", 0, 0, 0, "", false, "", "")

    override fun toString(): String {
        return id.toString() +
                '|' + name_id +
                '|' + capitale_id +
                '|' + abbrev
    }

}
