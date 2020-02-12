package com.remilapointe.laser.db

import androidx.annotation.NonNull
import androidx.room.*
import java.time.LocalDate
import java.util.*

@Entity(
    tableName = TransactionArticles.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = [Article.PRIM_KEY],
            childColumns = [TransactionArticles.TRANSACTION_ARTICLEID]
        )
    ]
)
data class TransactionArticles(
    @PrimaryKey(autoGenerate = true)
    @NonNull val id: Int,
    @ColumnInfo(index = true)
    val articleId: Int,
    val trType: String,
    val nb: Int,
    val prixHT: Double,
    val TVA: Double,
    val remise: Double,
    val date: LocalDate
) {
    companion object {
        const val ELEM = "transactionArticles"
        const val TABLE_NAME = "laser_$ELEM"
        const val PRIM_KEY = "id"
        const val TRANSACTION_ARTICLEID = "articleId"
        const val TRANSACTION_TRTYPE = "trType"
        const val TRANSACTION_NB = "nb"
        const val TRANSACTION_PRIXHT = "prixHT"
        const val TRANSACTION_TVA = "TVA"
        const val TRANSACTION_REMISE = "REMISE"
        const val TRANSACTION_DATE = "date"
        const val TRANSACTION_TYPE_ACHAT = "ACHAT"
        const val TRANSACTION_TYPE_VENTE = "VENTE"
    }

}
