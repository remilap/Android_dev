package eu.remilapointe.scorejeux.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.scorejeux.entity.Joueur

@Dao
interface JoueurDao {
    @Query("SELECT * FROM " + Joueur.TABLE_NAME + " ORDER BY " + Joueur.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Joueur>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(country: Joueur)

    @Query("DELETE FROM " + Joueur.TABLE_NAME + " WHERE " + Joueur.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + Joueur.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + Joueur.TABLE_NAME + " WHERE " + Joueur.PRIM_KEY + " = :key")
    fun get(key: Long): Joueur

    @Query("SELECT * FROM " + Joueur.TABLE_NAME + " WHERE " + Joueur.NAME + " = :key")
    fun get(name: String): Joueur

    @Query("UPDATE " + Joueur.TABLE_NAME + " SET " +
            Joueur.NAME + " = :value" +
            " WHERE " + Joueur.PRIM_KEY + " = :key")
    suspend fun update(key: Long, value: String) : Int

}
