package eu.remilapointe.country.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.log4k.d
import eu.remilapointe.country.dao.CountryDao
import eu.remilapointe.country.dao.LongWithDateDao
import eu.remilapointe.country.dao.StringWithDateDao
import eu.remilapointe.country.dao.StringWithLanguageDao
import eu.remilapointe.country.entity.Country
import eu.remilapointe.country.entity.LongWithDate
import eu.remilapointe.country.entity.StringWithDate
import eu.remilapointe.country.entity.StringWithLanguage
import java.time.LocalDate

@Database(
    entities = [Country::class, LongWithDate::class, StringWithDate::class, StringWithLanguage::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class CountryDb : RoomDatabase() {

    abstract fun countryDao(): CountryDao
    abstract fun longWithDateDao(): LongWithDateDao
    abstract fun stringWithDateDao(): StringWithDateDao
    abstract fun stringWithLanguageDao(): StringWithLanguageDao

    companion object {
        @Volatile
        private var INSTANCE: CountryDb? = null

        fun getDatabase(
            context: Context
        ): CountryDb {
            d("getDatabase")
            return INSTANCE ?: synchronized(this) {
                // create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountryDb::class.java,
                    "country_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(CountryDatabaseCallback())
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
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, CountryDb::class.java).build()
        }

        private class CountryDatabaseCallback : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let {
                    populateDatabase(it.countryDao())
                }
            }

            /**
             * Populate the database in a new coroutine.
             * If you want to start with more words, just add them.
             */
            fun populateDatabase(countryDao: CountryDao) {
                countryDao.removeAll()
                val name1 = StringWithLanguage(1, StringWithLanguage.STRING_INFO_NAME, 1, "fr", "France")
                val capitale1 = StringWithLanguage(2, StringWithLanguage.STRING_INFO_CAPITALE, 1, "fr", "Paris")
                var country = Country(
                    1,
                    name1.id,
                    capitale1.id,
                    "FR",
                    0,
                    0,
                    LocalDate.of(1972, 1, 1),
                    null,
                    true,
                    "français",
                    0,
                    "")
                countryDao.insert(country)
                val name2 = StringWithLanguage(3, StringWithLanguage.STRING_INFO_NAME, 2, "fr", "Espagne")
                val capitale2 = StringWithLanguage(4, StringWithLanguage.STRING_INFO_CAPITALE, 2, "fr", "Madrid")
                country = Country(
                    2,
                    name2.id,
                    capitale2.id,
                    "SP",
                    0,
                    0,
                    LocalDate.of(1972, 1, 1),
                    null,
                    true,
                    "espagnol",
                    0,
                    "")
                countryDao.insert(country)
                val name3 = StringWithLanguage(5, StringWithLanguage.STRING_INFO_NAME, 3, "fr", "Allemagne")
                val capitale3 = StringWithLanguage(6, StringWithLanguage.STRING_INFO_CAPITALE, 3, "fr", "Berlin")
                country = Country(
                    3,
                    name3.id,
                    capitale3.id,
                    "DE",
                    0,
                    0,
                    LocalDate.of(1972, 1, 1),
                    null,
                    true,
                    "allemand",
                    0,
                    "")
                countryDao.insert(country)
                val name4 = StringWithLanguage(7, StringWithLanguage.STRING_INFO_NAME, 4, "fr", "Belgique")
                val capitale4 = StringWithLanguage(8, StringWithLanguage.STRING_INFO_CAPITALE, 4, "fr", "Bruxelles")
                country = Country(
                    4,
                    name4.id,
                    capitale4.id,
                    "BE",
                    0,
                    0,
                    LocalDate.of(1972, 1, 1),
                    null,
                    true,
                    "flamand,français",
                    0,
                    "")
                countryDao.insert(country)
                val name5 = StringWithLanguage(9, StringWithLanguage.STRING_INFO_NAME, 5, "fr", "Suisse")
                val capitale5 = StringWithLanguage(10, StringWithLanguage.STRING_INFO_CAPITALE, 5, "fr", "Genève")
                country = Country(
                    5,
                    name5.id,
                    capitale5.id,
                    "CH",
                    0,
                    0,
                    LocalDate.of(1972, 1, 1),
                    null,
                    true,
                    "français,allemand",
                    0,
                    "")
                countryDao.insert(country)
            }
        }

    }
}
