package com.remilapointe.roomwordsample.db

import androidx.room.TypeConverter

class StringToListConverter {

    companion object {
        private val separator: CharSequence = " "

        @TypeConverter
        @JvmStatic
        fun wordsListToString(words: MutableList<Word>?): String? {
            return words?.map { it.word }?.joinToString(separator = separator)
        }

        @TypeConverter
        @JvmStatic
        fun stringToWordsList(phrase: String?): MutableList<Word>? {
            return phrase?.split(separator.toString())?.map { Word(it, it) }?.toMutableList()
        }

    }
}
