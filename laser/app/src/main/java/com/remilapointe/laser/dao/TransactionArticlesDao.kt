package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.TransactionArticles
import java.time.LocalDate
import java.util.*

@Dao
interface TransactionArticlesDao {

    @Query("SELECT * FROM " + TransactionArticles.TABLE_NAME + " ORDER BY " + TransactionArticles.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<TransactionArticles>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionArticles: TransactionArticles)

    @Query("DELETE FROM " + TransactionArticles.TABLE_NAME + " WHERE " + TransactionArticles.PRIM_KEY + " = :key")
    fun remove(key: Int) : Int

    @Query("SELECT * FROM " + TransactionArticles.TABLE_NAME + " WHERE " + TransactionArticles.PRIM_KEY + " = :key")
    fun get(key: Int) : TransactionArticles

    @Query("UPDATE " + TransactionArticles.TABLE_NAME + " SET " +
            TransactionArticles.TRANSACTION_ARTICLEID + " = :artId, " +
            TransactionArticles.TRANSACTION_TRTYPE + " = :trType, " +
            TransactionArticles.TRANSACTION_NB + " = :nb, " +
            TransactionArticles.TRANSACTION_PRIXHT + " = :prixHT, " +
            TransactionArticles.TRANSACTION_TVA + " = :tva, " +
            TransactionArticles.TRANSACTION_REMISE + " = :remise, " +
            TransactionArticles.TRANSACTION_DATE + " = :date " +
            " WHERE " + TransactionArticles.PRIM_KEY + " = :key")
    suspend fun update(key: Int, artId: Int, trType: String, nb: Int, prixHT: Double, tva: Double, remise: Double, date: LocalDate) : Int

}
