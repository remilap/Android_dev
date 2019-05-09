package com.remilapointe.country.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.remilapointe.country.dao.LongWithDateDao
import com.remilapointe.country.entity.LongWithDate

@Database(entities = [LongWithDate::class], version = 1, exportSchema = false)
abstract class LongWithDateDb : RoomDatabase() {
    abstract fun longWithDateDao(): LongWithDateDao

    companion object {
        const val DB_NAME = "longWithDate"

        private var INSTANCE: LongWithDateDb? = null

        fun getInstance(context: Context): LongWithDateDb {
            if (INSTANCE == null) {
                synchronized(LongWithDateDb::class) {
                    INSTANCE = buildDatabase(context).build()
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, LongWithDateDb::class.java, DB_NAME)

    }
}