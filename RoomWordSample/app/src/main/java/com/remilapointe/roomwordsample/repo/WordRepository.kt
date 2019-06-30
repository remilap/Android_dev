package com.remilapointe.roomwordsample.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.remilapointe.roomwordsample.db.Word
import com.remilapointe.roomwordsample.db.WordDao

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<MutableList<Word>> = wordDao.getAllWords()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @WorkerThread
    suspend fun insert(word: String, trad: String) {
        val wordObj = Word(word, trad)
        wordDao.insert(wordObj)
    }

    @WorkerThread
    suspend fun removeAt(pos: Int) {
        val wordObj = allWords.value?.get(pos)
        wordDao.removeAWord(wordObj!!)
    }

}
