package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = Produit.TABLE_NAME,
    //primaryKeys = ["id", "coloriId", "tailleId", "placeLogoId"],
    foreignKeys = [
        ForeignKey(
            entity = Colori::class,
            parentColumns = ["id"],
            childColumns = ["coloriId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Taille::class,
            parentColumns = ["id"],
            childColumns = ["tailleId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlaceLogo::class,
            parentColumns = ["id"],
            childColumns = ["placeLogoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Produit(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    val coloriId: Int,
    @ColumnInfo(index = true)
    val tailleId: Int,
    @ColumnInfo(index = true)
    val placeLogoId: Int
) {
    companion object {
        const val TABLE_NAME = "laser_produit"
        const val PRIM_KEY = "id"
    }

}
