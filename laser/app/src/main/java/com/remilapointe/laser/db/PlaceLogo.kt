package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PlaceLogo.TABLE_NAME)
data class PlaceLogo(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @NonNull val elem: String
) {
    companion object {
        const val TABLE_NAME = "laser_place_logo"
        const val SORT_FIELD = "elem"
    }
}
