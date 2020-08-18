package eu.remilapointe.country.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.country.entity.StringWithLanguage

@Dao
interface StringWithLanguageDao {
    @Query("SELECT * FROM " + StringWithLanguage.TABLE_NAME + " ORDER BY " + StringWithLanguage.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<StringWithLanguage>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(country: StringWithLanguage)

    @Query("DELETE FROM " + StringWithLanguage.TABLE_NAME + " WHERE " + StringWithLanguage.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + StringWithLanguage.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + StringWithLanguage.TABLE_NAME + " WHERE " + StringWithLanguage.PRIM_KEY + " = :key")
    fun get(key: Long): StringWithLanguage

    @Query("SELECT * FROM " + StringWithLanguage.TABLE_NAME + " WHERE " + StringWithLanguage.STRING_INFO + " = :info AND " + StringWithLanguage.STRING_COUNTRY_ID + " = :countryId")
    fun getStringInfoForCountry(info: Int, countryId: Long): LiveData<MutableList<StringWithLanguage>>

    @Query(
        "UPDATE " + StringWithLanguage.TABLE_NAME + " SET " +
                StringWithLanguage.STRING_INFO + " = :info AND " +
                StringWithLanguage.STRING_COUNTRY_ID + " = :countryId AND " +
                StringWithLanguage.STRING_LANGUAGE + " = :language AND " +
                StringWithLanguage.STRING_VALUE + " = :value" +
                " WHERE " + StringWithLanguage.PRIM_KEY + " = :key"
    )
    suspend fun update(key: Long, info: Int, countryId: Long, language: String, value: String): Int

}
