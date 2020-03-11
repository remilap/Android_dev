package eu.remilapointe.scorejeux.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.scorejeux.entity.Jeu

@Dao
interface JeuDao {
    @Query("SELECT * FROM " + Jeu.TABLE_NAME + " ORDER BY " + Jeu.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Jeu>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(country: Jeu)

    @Query("DELETE FROM " + Jeu.TABLE_NAME + " WHERE " + Jeu.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + Jeu.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + Jeu.TABLE_NAME + " WHERE " + Jeu.PRIM_KEY + " = :key")
    fun get(key: Long): Jeu

    @Query("SELECT * FROM " + Jeu.TABLE_NAME + " WHERE " + Jeu.NAME + " = :key")
    fun get(name: String): Jeu

    @Query("UPDATE " + Jeu.TABLE_NAME + " SET " +
            Jeu.NAME + " = :value" +
            " WHERE " + Jeu.PRIM_KEY + " = :key")
    suspend fun update(key: Long, value: String) : Int

}
