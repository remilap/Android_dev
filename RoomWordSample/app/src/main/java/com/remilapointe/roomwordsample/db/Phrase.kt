package com.remilapointe.roomwordsample.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrase_table")
data class Phrase(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val mots: List<Word>

)
