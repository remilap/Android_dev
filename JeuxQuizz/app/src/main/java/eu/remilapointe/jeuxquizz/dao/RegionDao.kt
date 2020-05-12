package eu.remilapointe.jeuxquizz.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.jeuxquizz.entity.Region

@Dao
interface RegionDao {
    @Query("SELECT * FROM " + Region.TABLE_NAME + " ORDER BY " + Region.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Region>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(country: Region)

    @Query("DELETE FROM " + Region.TABLE_NAME + " WHERE " + Region.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + Region.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + Region.TABLE_NAME + " WHERE " + Region.PRIM_KEY + " = :key")
    fun get(key: Long): Region

    @Query("SELECT * FROM " + Region.TABLE_NAME + " WHERE " + Region.REGION_NUM + " = :num")
    fun getByNum(num: Int): Region

    @Query("SELECT * FROM " + Region.TABLE_NAME + " WHERE " + Region.REGION_NAME + " = :name")
    fun getByName(name: String): Region

    @Query(
        "UPDATE " + Region.TABLE_NAME + " SET " +
                Region.REGION_NUM + " = :num AND " +
                Region.REGION_NAME + " = :name" +
                " WHERE " + Region.PRIM_KEY + " = :key"
    )
    suspend fun update(key: Long, num: Int, name: String): Int
}
