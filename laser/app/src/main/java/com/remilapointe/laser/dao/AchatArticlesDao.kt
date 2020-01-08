package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.AchatArticles
import java.util.*

@Dao
interface AchatArticlesDao {

    @Query("SELECT * FROM " + AchatArticles.TABLE_NAME + " ORDER BY " + AchatArticles.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<AchatArticles>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achatArticles: AchatArticles)

    @Query("DELETE FROM " + AchatArticles.TABLE_NAME + " WHERE " + AchatArticles.PRIM_KEY + " = :key")
    fun remove(key: Int)

    @Query("SELECT * FROM " + AchatArticles.TABLE_NAME + " WHERE " + AchatArticles.PRIM_KEY + " = :key")
    fun get(key: Int) : AchatArticles

    @Query("UPDATE " + AchatArticles.TABLE_NAME + " SET " +
            AchatArticles.ARTICLEID + " = :artId, " +
            AchatArticles.NB + " = :nb, " +
            AchatArticles.PRIXACHATHT + " = :prixHT, " +
            AchatArticles.DATE + " = :date " +
            " WHERE " + AchatArticles.PRIM_KEY + " = :key")
    suspend fun update(key: Int, artId: Int, nb: Int, prixHT: Double, date: Date)

}
