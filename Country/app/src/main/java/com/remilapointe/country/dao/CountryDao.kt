package com.remilapointe.country.dao

import androidx.paging.DataSource
import androidx.room.*
import com.remilapointe.country.db.CountryDb
import com.remilapointe.country.entity.Country

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCountry(country: Country)

    @Insert
    fun addCountry(countries: List<Country>)

    @Update
    fun updateCountry(country: Country)

    @Delete
    fun reallyDeleteCountry(country: Country)

    @Query("SELECT * FROM ${CountryDb.DB_NAME} ORDER BY name_id")
    fun listCountriesByName(): DataSource.Factory<Int, Country>

    @Query("SELECT * FROM ${CountryDb.DB_NAME} WHERE id = :id")
    fun getCountryById(id: Long): Country

}
