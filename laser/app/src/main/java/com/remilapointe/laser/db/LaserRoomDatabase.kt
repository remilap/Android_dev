package com.remilapointe.laser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.remilapointe.laser.dao.ColoriDao
import com.remilapointe.laser.dao.PlaceLogoDao
import com.remilapointe.laser.dao.ProduitDao
import com.remilapointe.laser.dao.TailleDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Colori::class, Taille::class, PlaceLogo::class, Produit::class], version = 2, exportSchema = false)
abstract class LaserRoomDatabase : RoomDatabase() {
    abstract fun coloriDao(): ColoriDao
    abstract fun tailleDao(): TailleDao
    abstract fun placeLogoDao(): PlaceLogoDao
    abstract fun produitDao(): ProduitDao

    companion object {
        @Volatile
        private var INSTANCE: LaserRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
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
                    .addCallback(LaserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class LaserDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
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