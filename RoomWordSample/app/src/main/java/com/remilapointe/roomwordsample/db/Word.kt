package com.remilapointe.roomwordsample.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Word.TN_WORD)
data class Word(
    @PrimaryKey
    val word: String,

    val trad: String

) {
    companion object {
        const val TN_WORD = "word_table"
    }
}
