package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = ArticleFacture.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ArticlesEnStock::class,
            parentColumns = [ArticlesEnStock.PRIM_KEY],
            childColumns = [ArticleFacture.PRIM_KEY],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ArticleFacture(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @NonNull val qtArticleId: Int,
    val TVA: Double,
    val remise: Double
    ) {
    companion object {
        const val ELEM = "articleFacture"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val QUANTITE_ARTICLE_ID = "qtArticleId"
        const val TVA = "TVA"
        const val REMISE = "remise"
    }

}
