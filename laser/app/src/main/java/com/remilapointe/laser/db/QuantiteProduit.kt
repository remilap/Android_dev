package com.remilapointe.laser.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = QuantiteProduit.TABLE_NAME)
data class QuantiteProduit(
    @PrimaryKey
    val reference: String,
    val idProduit: Int,
    val nb: Int
) {
    companion object {
        const val TABLE_NAME = "laser_quantiteproduit"
        const val PRIM_KEY = "reference"
    }
}