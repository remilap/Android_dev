package com.remilapointe.laser.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.remilapointe.laser.dao.*

@Database(
    entities = [Produit::class, Colori::class, Taille::class, PlaceLogo::class, Article::class, ArticlesEnStock::class, AchatArticles::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class LaserRoomDatabase : RoomDatabase() {
    abstract fun produitDao(): ProduitDao
    abstract fun coloriDao(): ColoriDao
    abstract fun tailleDao(): TailleDao
    abstract fun placeLogoDao(): PlaceLogoDao
    abstract fun articleDao(): ArticleDao
    abstract fun articlesEnStockDao(): ArticlesEnStockDao

    companion object {
        @Volatile
        private var INSTANCE: LaserRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): LaserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LaserRoomDatabase::class.java,
                    "laser_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(LaserDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Switches the internal implementation with an empty in-memory database.
         *
         * @param context The context.
         */
        @VisibleForTesting
        public fun switchToInMemory(context: Context) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, LaserRoomDatabase::class.java).build()
        }

        private class LaserDatabaseCallback : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let {
                    populateDatabase()
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