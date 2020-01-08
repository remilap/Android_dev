package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.Stock

@Dao
interface StockDao {

    @Query("SELECT * FROM " + Stock.TABLE_NAME + " ORDER BY " + Stock.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Stock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: Stock)

    @Query("DELETE FROM " + Stock.TABLE_NAME + " WHERE " + Stock.PRIM_KEY + " = :key")
    suspend fun remove(key: Int)

    @Query("SELECT * FROM " + Stock.TABLE_NAME + " WHERE " + Stock.PRIM_KEY + " = :key")
    fun get(key: Int) : Stock

    @Query("UPDATE " + Stock.TABLE_NAME + " SET " +
            Stock.TITRESTOCK + " = :titStock, " +
            Stock.ARTICLESENSTOCKLIST + " = :artEnStockList " +
            " WHERE " + Stock.PRIM_KEY + " = :key")
    suspend fun update(key: Int, titStock: String, artEnStockList: List<Int>)

}
