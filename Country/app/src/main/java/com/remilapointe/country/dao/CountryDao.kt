package com.remilapointe.country.dao

import androidx.paging.DataSource
import androidx.room.*
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

    @Query("SELECT * FROM countries ORDER BY name_fr")
    fun listCountriesByNameFr(): DataSource.Factory<Int, Country>

    @Query("SELECT * FROM countries ORDER BY name_en")
    fun listCountriesByNameEn(): DataSource.Factory<Int, Country>

    @Query("SELECT * FROM countries WHERE id = :id")
    fun getCountryById(id: Long): Country

}
