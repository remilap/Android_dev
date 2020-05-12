package eu.remilapointe.jeuxquizz.db


import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import eu.remilapointe.jeuxquizz.R
import eu.remilapointe.jeuxquizz.dao.DepartementDao
import eu.remilapointe.jeuxquizz.dao.RegionDao
import eu.remilapointe.jeuxquizz.dao.VilleDao
import eu.remilapointe.jeuxquizz.entity.Departement
import eu.remilapointe.jeuxquizz.entity.Region
import eu.remilapointe.jeuxquizz.entity.Ville

@Database(
    entities = [Region::class, Departement::class, Ville::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class QuizzDb : RoomDatabase() {
    abstract fun regionDao(): RegionDao
    abstract fun departementDao(): DepartementDao
    abstract fun villeDao(): VilleDao

    companion object {
        @Volatile
        private var INSTANCE: QuizzDb? = null

        fun getDatabase(
            context: Context
        ): QuizzDb {
            return INSTANCE ?: synchronized(this) {
                // create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizzDb::class.java,
                    "quizz_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(QuizzDbCallback())
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
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, QuizzDb::class.java)
                .build()
        }

        private class QuizzDbCallback : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let {
//                    populateDatabase(it.regionDao(), it.departementDao(), it.villeDao())
                    populateDatabase()
                }
            }

            /**
             * Populate the database in a new coroutine.
             */
//            fun populateDatabase(regionDao: RegionDao, departementDao: DepartementDao, villeDao: VilleDao) {
            fun populateDatabase() {
/*
                regionDao.removeAll()

                val auvergne = Region(0,1,"Auvergne-Rhône-Alpes")
                regionDao.insert(auvergne)
                val hautsDeFrance = Region(0,2,"Hauts-de-France")
                regionDao.insert(hautsDeFrance)
                val provence = Region(0,3,"Provence-Alpes-Côte d'Azur")
                regionDao.insert(provence)

                var dept = Departement(0, "Ain", 1, auvergne.num, 1)
                departementDao.insert(dept)
                var ville = Ville(0, "Bourg-en-Bresse", dept.id)
                villeDao.insert(ville)
                dept = Departement(0,"Allier", 3, auvergne.id, 2)
                departementDao.insert(dept)
*/
            }
        }

    }
}
