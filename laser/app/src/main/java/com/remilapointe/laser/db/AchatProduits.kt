package com.remilapointe.laser.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AchatProduits.TABLE_NAME)
data class AchatProduits(
    @PrimaryKey
    val idAchat: Int,

    val produits: List<QuantiteProduit>

) {
    companion object {
        const val TABLE_NAME = "laser_achatproduits"
        const val PRIM_KEY = "idAchat"
    }

}
