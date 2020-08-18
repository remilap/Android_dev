package eu.remilapointe.country.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.country.entity.StringWithDate
import java.time.LocalDate

@Dao
interface StringWithDateDao {
    @Query("SELECT * FROM " + StringWithDate.TABLE_NAME + " ORDER BY " + StringWithDate.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<StringWithDate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stringWithDate: StringWithDate)

    @Query("DELETE FROM " + StringWithDate.TABLE_NAME + " WHERE " + StringWithDate.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + StringWithDate.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + StringWithDate.TABLE_NAME + " WHERE " + StringWithDate.PRIM_KEY + " = :key")
    fun get(key: Long) : StringWithDate?

    @Query("SELECT * FROM " + StringWithDate.TABLE_NAME + " WHERE " + StringWithDate.STRING_INFO + " = :info AND " + StringWithDate.STRING_COUNTRY_ID + " = :countryId")
    fun getStringInfoForCountry(info: Int, countryId: Long): LiveData<MutableList<StringWithDate>>

    @Query(
        "UPDATE " + StringWithDate.TABLE_NAME + " SET " +
                StringWithDate.STRING_INFO + " = :info AND " +
                StringWithDate.STRING_COUNTRY_ID + " = :countryId AND " +
                StringWithDate.STRING_DATE + " = :date AND " +
                StringWithDate.STRING_VALUE + " = :value" +
                " WHERE " + StringWithDate.PRIM_KEY + " = :key"
    )
    suspend fun update(key: Long, info: Int, countryId: Long, date: LocalDate, value: String): Int

}
