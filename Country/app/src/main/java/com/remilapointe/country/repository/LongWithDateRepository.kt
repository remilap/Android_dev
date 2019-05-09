package com.remilapointe.country.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.remilapointe.country.dao.LongWithDateDao
import com.remilapointe.country.db.LongWithDateDb
import com.remilapointe.country.entity.LongWithDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LongWithDateRepository : CoroutineScope{

    private lateinit var longWithDateDao: LongWithDateDao
    private var insertResult : MutableLiveData<Int> = MutableLiveData()

    constructor(context: Context) {
        var db = LongWithDateDb.getInstance(context)
        longWithDateDao = db.longWithDateDao()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun insert(longWithDate: LongWithDate) {
        insertAsync(longWithDate)
    }

    fun getInsertResult() : MutableLiveData<Int> {
        return insertResult
    }

    private fun insertAsync(longWithDate: LongWithDate) = launch {
        try {
            longWithDateDao.insert(longWithDate)
            insertResult.postValue(1)
        } catch (e : Exception) {
            insertResult.postValue(0)
        }
    }

}
