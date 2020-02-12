package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(
    tableName = ArticlesEnStock.TABLE_NAME,
    //primaryKeys = ["id", "produitId"],
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = [Article.PRIM_KEY],
            childColumns = [ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ArticlesEnStock(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    @NonNull val articleId: Int,
    val nb: Int,
    val nbAchetes: Int,
    val dateAchat: LocalDate,
    val prixAchatHT: Double
) {
    companion object {
        const val ELEM = "articlesEnStock"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val ARTICLEENSTOCK_ARTICLEID = "articleId"
        const val ARTICLEENSTOCK_NB = "nb"
        const val ARTICLEENSTOCK_NBACHETES = "nbAchetes"
        const val ARTICLEENSTOCK_DATEACHAT = "dateAchat"
        const val ARTICLEENSTOCK_PRIXACHATHT = "prixAchatHT"
    }
}