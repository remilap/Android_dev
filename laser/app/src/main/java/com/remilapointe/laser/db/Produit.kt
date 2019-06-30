package com.remilapointe.laser.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = Produit.TABLE_NAME,
    primaryKeys = ["id", "coloriId", "tailleId", "placeLogoId"],
    foreignKeys = [
        ForeignKey(
            entity = Colori::class,
            parentColumns = ["id"],
            childColumns = ["elem"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Taille::class,
            parentColumns = ["id"],
            childColumns = ["elem"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlaceLogo::class,
            parentColumns = ["id"],
            childColumns = ["elem"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Produit(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val coloriId: Int,
    val tailleId: Int,
    val placeLogoId: Int
) {
    companion object {
        const val TABLE_NAME = "laser_produit"
        const val PRIM_KEY = "id"
    }

}
