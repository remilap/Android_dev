package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.*
import java.util.*

@Entity(
    tableName = AchatArticles.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = [Article.PRIM_KEY],
            childColumns = [AchatArticles.ARTICLEID]
        )
    ]
)
data class AchatArticles(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    val articleId: Int,
    val nb: Int,
    val prixAchatHT: Double,
    val date: Date
    ) {
    companion object {
        const val ELEM = "achatArticles"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val ARTICLEID = "articleId"
        const val NB = "nb"
        const val PRIXACHATHT = "prixAchatHT"
        const val DATE = "date"
    }

}
