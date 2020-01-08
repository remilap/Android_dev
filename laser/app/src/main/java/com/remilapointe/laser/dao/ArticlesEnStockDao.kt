package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.ArticlesEnStock

@Dao
interface ArticlesEnStockDao {

    @Query("SELECT * FROM " + ArticlesEnStock.TABLE_NAME + " ORDER BY " + ArticlesEnStock.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<ArticlesEnStock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articlesEnStock: ArticlesEnStock)

    @Query("DELETE FROM " + ArticlesEnStock.TABLE_NAME + " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    suspend fun remove(key: Int)

    @Query("SELECT * FROM " + ArticlesEnStock.TABLE_NAME + " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    fun get(key: Int) : ArticlesEnStock

    @Query("UPDATE " + ArticlesEnStock.TABLE_NAME + " SET " +
            ArticlesEnStock.ARTICLE_ID + " = :artId, " +
            ArticlesEnStock.NB + " = :nb " +
            " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    suspend fun update(key: Int, artId: Int, nb: Int)

}
