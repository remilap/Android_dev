package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.ArticlesEnStock
import java.time.LocalDate
import java.util.*

@Dao
interface ArticlesEnStockDao {

    @Query("SELECT * FROM " + ArticlesEnStock.TABLE_NAME + " ORDER BY " + ArticlesEnStock.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<ArticlesEnStock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articlesEnStock: ArticlesEnStock)

    @Query("DELETE FROM " + ArticlesEnStock.TABLE_NAME + " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    suspend fun remove(key: Int): Int

    @Query("SELECT * FROM " + ArticlesEnStock.TABLE_NAME + " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    fun get(key: Int): ArticlesEnStock

    @Query("UPDATE " + ArticlesEnStock.TABLE_NAME + " SET " +
            ArticlesEnStock.ARTICLEENSTOCK_ARTICLEID + " = :artId, " +
            ArticlesEnStock.ARTICLEENSTOCK_NB + " = :nb, " +
            ArticlesEnStock.ARTICLEENSTOCK_NBACHETES + " = :nbAchetes, " +
            ArticlesEnStock.ARTICLEENSTOCK_DATEACHAT + " = :dateAchat, " +
            ArticlesEnStock.ARTICLEENSTOCK_PRIXACHATHT + " = :prixAchat " +
            " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    suspend fun update(key: Int, artId: Int, nb: Int, nbAchetes: Int, dateAchat: LocalDate, prixAchat: Double): Int

    @Query("SELECT SUM(" + ArticlesEnStock.ARTICLEENSTOCK_NB + ") FROM " + ArticlesEnStock.TABLE_NAME + " WHERE " + ArticlesEnStock.PRIM_KEY + " = :key")
    fun getNbArticlesEnStock(key: Int): Int

}
