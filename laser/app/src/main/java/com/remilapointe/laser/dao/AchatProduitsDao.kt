package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.AchatProduits

@Dao
interface AchatProduitsDao {

    @Query("SELECT * FROM " + AchatProduits.TABLE_NAME + " ORDER BY " + AchatProduits.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<AchatProduits>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achatProduits: AchatProduits)

    @Query("DELETE FROM " + AchatProduits.TABLE_NAME + " WHERE " + AchatProduits.PRIM_KEY + " = :key")
    fun remove(key: String)

    @Query("SELECT * FROM " + AchatProduits.TABLE_NAME + " WHERE " + AchatProduits.PRIM_KEY + " = :key")
    fun get(key: String) : AchatProduits

}
