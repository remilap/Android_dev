package eu.remilapointe.jeuxquizz.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.remilapointe.jeuxquizz.entity.Departement

@Dao
interface DepartementDao {
    @Query("SELECT * FROM " + Departement.TABLE_NAME + " ORDER BY " + Departement.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Departement>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(country: Departement)

    @Query("DELETE FROM " + Departement.TABLE_NAME + " WHERE " + Departement.PRIM_KEY + " = :key")
    suspend fun remove(key: Long): Int

    @Query("DELETE FROM " + Departement.TABLE_NAME)
    fun removeAll(): Int

    @Query("SELECT * FROM " + Departement.TABLE_NAME + " WHERE " + Departement.PRIM_KEY + " = :key")
    fun get(key: Long): Departement

    @Query("SELECT * FROM " + Departement.TABLE_NAME + " WHERE " + Departement.DEPT_NAME + " = :name")
    fun getByName(name: String): Departement

    @Query("SELECT * FROM " + Departement.TABLE_NAME + " WHERE " + Departement.DEPT_NUMERO + " = :numero")
    fun getByNum(numero: String): Departement

    @Query(
        "UPDATE " + Departement.TABLE_NAME + " SET " +
                Departement.DEPT_NAME + " = :name AND " +
                Departement.DEPT_NUMERO + " = :numero AND " +
                Departement.DEPT_REGIONID + " = :regionId AND " +
                Departement.DEPT_CHEFLIEUID + " = :cheflieuId AND " +
                Departement.DEPT_PRONOM + " = :pronom" +
                " WHERE " + Departement.PRIM_KEY + " = :key"
    )
    suspend fun update(key: Long, name: String, numero: String, regionId: Long, cheflieuId: Long, pronom: String): Int
}
