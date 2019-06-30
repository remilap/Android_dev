package com.remilapointe.roomwordsample.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.remilapointe.roomwordsample.db.*

class PhraseRepository(private val phraseDao: PhraseDao, private val wordDao: WordDao) {

    val allPhrases: LiveData<MutableList<Phrase>> = phraseDao.getAllPhrases()

    @WorkerThread
    suspend fun insert(sPhrase: String) {
        val phrase = Phrase(0, StringToListConverter.stringToWordsList(sPhrase)!!)
        phraseDao.insert(phrase)
        for (word: Word in phrase.mots) {
            wordDao.insert(word)
        }
    }

    @WorkerThread
    suspend fun remove(id: Long) {
        phraseDao.deleteAPhrase(id)
    }

}
