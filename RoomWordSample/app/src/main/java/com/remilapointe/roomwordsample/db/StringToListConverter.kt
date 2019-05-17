package com.remilapointe.roomwordsample.db

import androidx.room.TypeConverter

class StringToListConverter {

    companion object {
        val separator: CharSequence = " "

        @TypeConverter
        @JvmStatic
        fun wordsListToString(words: List<Word>?): String? {
            return words?.map { it.word }?.joinToString(separator = separator)
        }

        @TypeConverter
        @JvmStatic
        fun stringToWordsList(phrase: String?): List<Word>? {
            return phrase?.split(separator.toString())?.map { Word(it, it) }
        }

    }
}
