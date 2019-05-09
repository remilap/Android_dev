package com.remilapointe.mvp1.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.remilapointe.mvp1.model.Creature

@Dao
interface CreatureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(creature: Creature)

    @Delete
    fun clearCreatures(vararg creature: Creature)

    @Query("SELECT * FROM creature_table ORDER BY NAME ASC")
    fun getAllCreatures(): LiveData<List<Creature>>
}