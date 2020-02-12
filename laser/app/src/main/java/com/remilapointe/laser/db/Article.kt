package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    tableName = Article.TABLE_NAME,
    //primaryKeys = ["id", "coloriId", "tailleId", "placeLogoId"],
    foreignKeys = [
        ForeignKey(
            entity = Produit::class,
            parentColumns = [Produit.PRIM_KEY],
            childColumns = [Article.PRODUIT_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Colori::class,
            parentColumns = [Colori.PRIM_KEY],
            childColumns = [Article.COLORI_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Taille::class,
            parentColumns = [Taille.PRIM_KEY],
            childColumns = [Article.TAILLE_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlaceLogo::class,
            parentColumns = [PlaceLogo.PRIM_KEY],
            childColumns = [Article.PLACELOGO_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    val produitId: Int,
    @ColumnInfo(index = true)
    val coloriId: Int,
    @ColumnInfo(index = true)
    val tailleId: Int,
    @ColumnInfo(index = true)
    val placeLogoId: Int
) {

    companion object {
        const val ELEM = "article"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val PRODUIT_ID = "produitId"
        const val COLORI_ID = "coloriId"
        const val TAILLE_ID = "tailleId"
        const val PLACELOGO_ID = "placeLogoId"
    }

}
