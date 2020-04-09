package com.remilapointe.laser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.laser.db.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM " + Article.TABLE_NAME + " ORDER BY " + Article.PRIM_KEY + " ASC")
    fun getAll(): LiveData<MutableList<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(article: Article)

    @Query("DELETE FROM " + Article.TABLE_NAME + " WHERE " + Article.PRIM_KEY + " = :key")
    suspend fun remove(key: Int) : Int

    @Query("DELETE FROM " + Article.TABLE_NAME)
    suspend fun removeAll() : Int

    @Query("SELECT * FROM " + Article.TABLE_NAME + " WHERE " + Article.PRIM_KEY + " = :key")
    suspend fun get(key: Int) : Article?

    @Query("SELECT * FROM " + Article.TABLE_NAME + " WHERE " +
            Article.PRODUIT_ID + " = :proId AND " +
            Article.COLORI_ID + " = :colId AND " +
            Article.TAILLE_ID + " = :taiId AND " +
            Article.PLACELOGO_ID + " = :plaId"
    )
    suspend fun get(proId: Int, colId: Int, taiId: Int, plaId: Int) : Article?

    @Query("UPDATE " + Article.TABLE_NAME + " SET " +
            Article.PRODUIT_ID + " = :proId, " +
            Article.COLORI_ID + " = :colId, " +
            Article.TAILLE_ID + " = :taiId, " +
            Article.PLACELOGO_ID + " = :plaId " +
            " WHERE id= :key")
    suspend fun update(key: Int, proId: Int, colId: Int, taiId: Int, plaId: Int) : Int

}
