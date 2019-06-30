package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.QuantiteProduit

@Dao
interface QuantiteProduitDao {

    @Query("SELECT * FROM " + QuantiteProduit.TABLE_NAME + " ORDER BY " + QuantiteProduit.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<QuantiteProduit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quantiteProduit: QuantiteProduit)

    @Query("DELETE FROM " + QuantiteProduit.TABLE_NAME + " WHERE " + QuantiteProduit.PRIM_KEY + " = :key")
    fun remove(key: String)

    @Query("SELECT * FROM " + QuantiteProduit.TABLE_NAME + " WHERE " + QuantiteProduit.PRIM_KEY + " = :key")
    fun get(key: String) : QuantiteProduit

}
