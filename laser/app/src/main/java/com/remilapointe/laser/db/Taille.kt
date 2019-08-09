package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Taille.TABLE_NAME)
data class Taille(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @NonNull val elem: String
) {
    companion object {
        const val TABLE_NAME = "laser_taille"
        const val SORT_FIELD = "elem"
    }
}
