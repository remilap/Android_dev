package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.Colori

@Dao
interface ColoriDao {
    @Query("SELECT * FROM " + Colori.TABLE_NAME + " ORDER BY " + Colori.SORT_FIELD + " ASC")
    fun getAll(): LiveData<MutableList<Colori>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(colori: Colori)

    @Query("DELETE FROM " + Colori.TABLE_NAME + " WHERE " + Colori.PRIM_KEY + " = :key")
    suspend fun remove(key: Int) : Int

    @Query("SELECT * FROM " + Colori.TABLE_NAME + " WHERE " + Colori.PRIM_KEY + " = :key")
    fun get(key: Int) : Colori

    @Query("SELECT * FROM " + Colori.TABLE_NAME + " WHERE " + Colori.SORT_FIELD + " = :value")
    fun get(value: String) : Colori

    @Query("UPDATE " + Colori.TABLE_NAME + " SET " +
            Colori.SORT_FIELD + " = :value" +
            " WHERE " + Colori.PRIM_KEY + " = :key")
    suspend fun update(key: Int, value: String) : Int

}
