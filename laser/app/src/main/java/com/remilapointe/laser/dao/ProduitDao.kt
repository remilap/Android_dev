package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.Produit

@Dao
interface ProduitDao {
    @Query("SELECT * FROM " + Produit.TABLE_NAME + " ORDER BY " + Produit.SORT_FIELD + " ASC")
    fun getAll(): LiveData<MutableList<Produit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(produit: Produit)

    @Query("DELETE FROM " + Produit.TABLE_NAME + " WHERE " + Produit.PRIM_KEY + " = :key")
    suspend fun remove(key: Int)

    @Query("SELECT * FROM " + Produit.TABLE_NAME + " WHERE " + Produit.PRIM_KEY + " = :key")
    fun get(key: Int): Produit

    @Query("SELECT * FROM " + Produit.TABLE_NAME + " WHERE " + Produit.SORT_FIELD + " = :value")
    fun get(value: String): Produit

    @Query("UPDATE " + Produit.TABLE_NAME + " SET " +
            Produit.SORT_FIELD + " = :value" +
            " WHERE " + Produit.PRIM_KEY + " = :key")
    suspend fun update(key: Int, value: String)

}
