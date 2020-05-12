package eu.remilapointe.jeuxquizz.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.jeuxquizz.entity.Ville

@Dao
interface VilleDao {
    @Query("SELECT * FROM " + Ville.TABLE_NAME + " ORDER BY " + Ville.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Ville>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(country: Ville)

    @Query("DELETE FROM " + Ville.TABLE_NAME + " WHERE " + Ville.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + Ville.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + Ville.TABLE_NAME + " WHERE " + Ville.PRIM_KEY + " = :key")
    fun get(key: Long): Ville

    @Query("SELECT * FROM " + Ville.TABLE_NAME + " WHERE " + Ville.VILLE_NAME + " = :name")
    fun get(name: String): Ville

    @Query(
        "UPDATE " + Ville.TABLE_NAME + " SET " +
                Ville.VILLE_NAME + " = :name" +
                " WHERE " + Ville.PRIM_KEY + " = :key"
    )
    suspend fun update(key: Long, name: String): Int
}
