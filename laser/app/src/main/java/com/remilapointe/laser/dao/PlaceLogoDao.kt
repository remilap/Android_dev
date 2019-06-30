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

    @Query("DELETE FROM " + PlaceLogo.TABLE_NAME + " WHERE " + PlaceLogo.SORT_FIELD + " = :key")
    fun remove(key: String)

    @Query("SELECT * FROM " + PlaceLogo.TABLE_NAME + " WHERE " + PlaceLogo.SORT_FIELD + " = :key")
    fun get(key: String) : PlaceLogo

}
