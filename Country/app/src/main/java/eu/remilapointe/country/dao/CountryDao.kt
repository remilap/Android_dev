package eu.remilapointe.country.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import eu.remilapointe.country.entity.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM " + Country.TABLE_NAME + " ORDER BY " + Country.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Country>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(country: Country)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(country: Country)

    @Query("DELETE FROM " + Country.TABLE_NAME + " WHERE " + Country.PRIM_KEY + " = :key")
    suspend fun remove(key: Long) : Int

    @Query("DELETE FROM " + Country.TABLE_NAME)
    fun removeAll() : Int

    @Query("SELECT * FROM " + Country.TABLE_NAME + " WHERE " + Country.PRIM_KEY + " = :key")
    fun get(key: Long) : Country?

}
