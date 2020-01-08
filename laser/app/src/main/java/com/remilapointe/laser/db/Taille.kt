package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Taille.TABLE_NAME)
data class Taille(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    @NonNull val elem: String
) {
    companion object {
        const val ELEM = "taille"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val SORT_FIELD = "elem"
    }
}
