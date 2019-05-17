package com.remilapointe.roomwordsample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.remilapointe.roomwordsample.db.Phrase

@Dao
interface PhraseDao {
    @Query("SELECT * FROM phrase_table ORDER BY id ASC")
    fun getAllPhrases(): LiveData<List<Phrase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(phrase: Phrase)

    @Query("DELETE FROM phrase_table")
    fun deleteAll()

    @Query("DELETE FROM phrase_table WHERE id = :id")
    fun deleteAPhrase(id: Long)

}