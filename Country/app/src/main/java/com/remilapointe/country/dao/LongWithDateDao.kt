package eu.remilapointe.country.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.country.entity.LongWithDate
import java.time.LocalDate

@Dao
interface LongWithDateDao {
    @Query("SELECT * FROM " + LongWithDate.TABLE_NAME + " ORDER BY " + LongWithDate.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<LongWithDate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(longWithDate: LongWithDate)

    @Query("DELETE FROM " + LongWithDate.TABLE_NAME + " WHERE " + LongWithDate.PRIM_KEY + " = :key")
    suspend fun remove(key: Long) : Int

    @Query("DELETE FROM " + LongWithDate.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + LongWithDate.TABLE_NAME + " WHERE " + LongWithDate.PRIM_KEY + " = :key")
    fun get(key: Long) : LongWithDate?

    @Query("SELECT * FROM " + LongWithDate.TABLE_NAME + " WHERE " + LongWithDate.LONG_INFO + " = :info AND " + LongWithDate.LONG_COUNTRY_ID + " = :countryId")
    fun getLongInfoForCountry(info: Int, countryId: Long): LiveData<MutableList<LongWithDate>>

    @Query(
        "UPDATE " + LongWithDate.TABLE_NAME + " SET " +
                LongWithDate.LONG_INFO + " = :info AND " +
                LongWithDate.LONG_COUNTRY_ID + " = :countryId AND " +
                LongWithDate.LONG_DATE + " = :date AND " +
                LongWithDate.LONG_VALUE + " = :value" +
                " WHERE " + LongWithDate.PRIM_KEY + " = :key"
    )
    suspend fun update(key: Long, info: String, countryId: Long, date: LocalDate, value: Long): Int

}
