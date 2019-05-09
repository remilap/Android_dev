package com.remilapointe.country.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.country.entity.LongWithDate

@Dao
interface LongWithDateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(longWithDate: LongWithDate)

    @Query("SELECT * FROM longWithDate ORDER BY id")
    fun getAllValues(): DataSource.Factory<Int, LongWithDate>

}
