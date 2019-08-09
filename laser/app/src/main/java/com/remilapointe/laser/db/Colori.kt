package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Colori.TABLE_NAME)
data class Colori (
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @NonNull val elem: String

) {
    companion object {
        const val TABLE_NAME = "laser_colori"
        const val SORT_FIELD = "elem"
    }
}
