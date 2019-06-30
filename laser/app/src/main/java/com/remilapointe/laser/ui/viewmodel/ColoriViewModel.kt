package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.Colori
import com.remilapointe.laser.db.LaserRoomDatabase
import com.remilapointe.laser.repo.ColoriRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColoriViewModel(application: Application) : AndroidViewModel(application) {

    private val myRepo: ColoriRepo

    val allObjs: LiveData<MutableList<Colori>>

    init {
        val myDao = LaserRoomDatabase.getDatabase(application, viewModelScope).coloriDao()
        myRepo = ColoriRepo(myDao)
        allObjs = myRepo.allObjs
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(colori: String) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert coloriId: $colori")
        myRepo.insert(colori)
    }

    fun remove(colori: Colori) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get coloriId: ${colori.elem}")
        myRepo.remove(colori)
    }

    fun getAllObjs() : Array<Colori> {
        val tabSize = if (allObjs.value == null) 1 else allObjs.value!!.size
        val res = Array(size = tabSize) { i ->
            if (allObjs.value != null) {
                allObjs.value!![i]
            } else {
                Colori(0, "null")
            }
        }
        return res
    }

    fun getColoriById(id: Int) : Colori? {
        getAllObjs().forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    fun getValueForId(id: Int) : String? {
        return getColoriById(id)?.elem
    }

    fun getIdForValue(elem: String) : Int {
        getAllObjs().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

}
