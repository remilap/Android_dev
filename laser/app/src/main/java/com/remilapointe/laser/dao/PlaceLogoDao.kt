package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.PlaceLogo

@Dao
interface PlaceLogoDao {
    @Query("SELECT * FROM " + PlaceLogo.TABLE_NAME + " ORDER BY " + PlaceLogo.SORT_FIELD + " ASC")
    fun getAll(): LiveData<MutableList<PlaceLogo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(placeLogo: PlaceLogo)

    @Query("DELETE FROM " + PlaceLogo.TABLE_NAME + " WHERE " + PlaceLogo.PRIM_KEY + " = :key")
    fun remove(key: Int) : Int

    @Query("SELECT * FROM " + PlaceLogo.TABLE_NAME + " WHERE " + PlaceLogo.PRIM_KEY + " = :key")
    fun get(key: Int) : PlaceLogo

    @Query("SELECT * FROM " + PlaceLogo.TABLE_NAME + " WHERE " + PlaceLogo.SORT_FIELD + " = :value")
    fun get(value: String) : PlaceLogo

    @Query("UPDATE " + PlaceLogo.TABLE_NAME + " SET " +
            PlaceLogo.SORT_FIELD + " = :value" +
            " WHERE " + PlaceLogo.PRIM_KEY + " = :key")
    suspend fun update(key: Int, value: String) : Int

}
