package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = ArticlesEnStock.TABLE_NAME,
    //primaryKeys = ["id", "produitId"],
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = [Article.PRIM_KEY],
            childColumns = [ArticlesEnStock.ARTICLE_ID],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ArticlesEnStock(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    @NonNull val articleId: Int,
    val nb: Int//,
//    val dateEntreeStock: Date,
//    val prixAchat: Double
) {
    companion object {
        const val ELEM = "articlesEnStock"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val ARTICLE_ID = "articleId"
        const val NB = "nb"
        const val DATE_ENTREE_STOCK = "dateEntreeStock"
        const val PRIX_ACHAT = "prixAchat"
    }
}