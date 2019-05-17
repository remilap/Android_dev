package com.remilapointe.roomwordsample.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.remilapointe.roomwordsample.db.Phrase
import com.remilapointe.roomwordsample.db.PhraseDao
import com.remilapointe.roomwordsample.db.StringToListConverter

class PhraseRepository(private val phraseDao: PhraseDao) {

    val allPhrases: LiveData<List<Phrase>> = phraseDao.getAllPhrases()

    @WorkerThread
    suspend fun insert(sPhrase: String) {
        var phrase = Phrase(0, StringToListConverter.stringToWordsList(sPhrase)!!)
        phraseDao.insert(phrase)
    }

}
