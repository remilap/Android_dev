package eu.remilapointe.scorejeux.db


import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import eu.remilapointe.scorejeux.dao.JeuDao
import eu.remilapointe.scorejeux.dao.JoueurDao
import eu.remilapointe.scorejeux.entity.Jeu
import eu.remilapointe.scorejeux.entity.Joueur

@Database(
    entities = [Joueur::class, Jeu::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class JeuDatabase : RoomDatabase() {
    abstract fun JoueurDao(): JoueurDao
    abstract fun JeuDao(): JeuDao

    companion object {
        @Volatile
        private var INSTANCE: JeuDatabase? = null

        fun getDatabase(
            context: Context
        ): JeuDatabase {
            return INSTANCE ?: synchronized(this) {
                // create database here
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        JeuDatabase::class.java,
                        "jeu_database"
                    )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(JeuDatabaseCallback())
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
            INSTANCE =
                Room.inMemoryDatabaseBuilder(context.applicationContext, JeuDatabase::class.java)
                    .build()
        }

        private class JeuDatabaseCallback : RoomDatabase.Callback() {
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
