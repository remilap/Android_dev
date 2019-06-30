package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.Taille

@Dao
interface TailleDao {
    @Query("SELECT * FROM " + Taille.TABLE_NAME + " ORDER BY " + Taille.SORT_FIELD + " ASC")
    fun getAll(): LiveData<MutableList<Taille>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taille: Taille)

    @Query("DELETE FROM " + Taille.TABLE_NAME + " WHERE " + Taille.SORT_FIELD + " = :key")
    fun remove(key: String)

    @Query("SELECT * FROM " + Taille.TABLE_NAME + " WHERE " + Taille.SORT_FIELD + " = :key")
    fun get(key: String) : Taille

}
