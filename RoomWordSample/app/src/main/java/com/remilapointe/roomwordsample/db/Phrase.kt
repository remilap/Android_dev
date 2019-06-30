package com.remilapointe.roomwordsample.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Phrase.TN_PHRASE)
data class Phrase(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val mots: MutableList<Word>

) {
    companion object {
        const val TN_PHRASE = "phrase_table"
    }
}
