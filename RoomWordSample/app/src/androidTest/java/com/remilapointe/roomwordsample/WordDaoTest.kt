package com.remilapointe.roomwordsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.remilapointe.roomwordsample.db.Word
import com.remilapointe.roomwordsample.db.WordDao
import com.remilapointe.roomwordsample.db.WordRoomDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wordDao: WordDao
    private lateinit var db: WordRoomDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() = runBlocking {
        val word = Word("word")
        wordDao.insert(word)
        val allWords = wordDao.getAllWords().waitForValue()
        assertEquals(allWords[0].word, word.word)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() = runBlocking {
        val word = Word("aaaa")
        wordDao.insert(word)
        val word2 = Word("bbbb")
        wordDao.insert(word2)
        val allWords = wordDao.getAllWords().waitForValue()
        assertEquals(allWords[0].word, word.word)
        assertEquals(allWords[1].word, word2.word)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val word = Word("1111")
        wordDao.insert(word)
        val word2 = Word("2222")
        wordDao.insert(word2)
        wordDao.deleteAll()
        val allWords = wordDao.getAllWords().waitForValue()
        assertTrue(allWords.isEmpty())
    }

}
