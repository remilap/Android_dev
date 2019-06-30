package com.remilapointe.roomwordsample.db

import androidx.room.TypeConverter

class WordToStringConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun wordToString(word: Word?) : String? {
            return word?.word
        }

        @TypeConverter
        @JvmStatic
        fun stringToWord(str: String?) : Word? {
            return Word(str!!, str!!)
        }

    }

}
