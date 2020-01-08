package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Facture.TABLE_NAME)
data class Facture(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    val ref: String
) {

    companion object {
        const val ELEM = "facture"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
    }

}
