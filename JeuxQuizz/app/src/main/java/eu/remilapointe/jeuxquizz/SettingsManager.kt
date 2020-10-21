package eu.remilapointe.jeuxquizz

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsManager(context: Context) {

    val dataStore : DataStore<Preferences> = context.createDataStore(DATASTORE_NAME)

    suspend fun setSelectedQuiz(selQuiz: Int) {
        dataStore.edit {
            it[SELECTED_QUIZ] = selQuiz
        }
    }

    val selQuizPref: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { pref ->
            pref[SELECTED_QUIZ] ?: 0
        }

    suspend fun setHighScore(which: Int, value: Int) {
        dataStore.edit {
            it[HIGH_SCORE]?.set(which, value)
        }
    }

    val highScore: Flow<Array<Int>> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { pref ->
            pref[HIGH_SCORE] ?: emptyArray()
        }

    companion object {
        const val QUIZ_REFERENCE = "QUIZ_REFERENCE"
        const val DATASTORE_NAME = "QUIZ_DATASTORE"
        val SELECTED_QUIZ = preferencesKey<Int>("selected_quiz")
        val quizList = arrayOf("test", "animals", "camel", "sheep", "goat")
        val HIGH_SCORE = preferencesKey<Array<Int>>("high_scores")
    }
}
