package com.remilapointe.roomwordsample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Word::class, Phrase::class], version = 3, exportSchema = false)
@TypeConverters(StringToListConverter::class)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun phraseDao(): PhraseDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "Word_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(
                        WordDatabaseCallback(
                            scope
                        )
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                        populateDatabase()
                    }
                }
            }

            /**
             * Populate the database in a new coroutine.
             * If you want to start with more words, just add them.
             */
            fun populateDatabase() {
                /*
            fun populateDatabase(wordDao: WordDao, phraseDao: PhraseDao) {
                wordDao.deleteAll()

                var word = Word("Hello", "Bonjour")
                wordDao.insert(word)
                word = Word("World!", "à tous")
                wordDao.insert(word)
                */
            }
        }
    }

}
