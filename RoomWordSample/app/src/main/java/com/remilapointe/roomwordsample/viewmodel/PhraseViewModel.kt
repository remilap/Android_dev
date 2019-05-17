package com.remilapointe.roomwordsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.remilapointe.roomwordsample.db.Phrase
import com.remilapointe.roomwordsample.db.WordRoomDatabase
import com.remilapointe.roomwordsample.repo.PhraseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhraseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PhraseRepository
    val allPhrases: LiveData<List<Phrase>>

    init {
        val phraseDao = WordRoomDatabase.getDatabase(application, viewModelScope).phraseDao()
        repository = PhraseRepository(phraseDao)
        allPhrases = repository.allPhrases
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(sPhrase: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(sPhrase)
    }

}
