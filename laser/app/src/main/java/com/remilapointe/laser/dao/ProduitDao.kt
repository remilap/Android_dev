package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.Produit

@Dao
interface ProduitDao {
    @Query("SELECT * FROM " + Produit.TABLE_NAME + " ORDER BY " + Produit.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Produit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(produit: Produit)

    @Query("DELETE FROM " + Produit.TABLE_NAME + " WHERE " + Produit.PRIM_KEY + " = :id")
    fun remove(id: Int)

    @Query("SELECT * FROM " + Produit.TABLE_NAME + " WHERE " + Produit.PRIM_KEY + " = :id")
    fun get(id: Int) : Produit

}
