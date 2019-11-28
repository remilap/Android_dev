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

    val allColoris: LiveData<MutableList<Colori>>

    init {
        d("ColoriViewModel:init")
        val myDao = LaserRoomDatabase.getDatabase(application).coloriDao()
        myRepo = ColoriRepo(myDao)
        allColoris = myRepo.allColoris
        if (allColoris.value == null) {
            d("ColoriViewModel:getAllColoris, size: 0")
        } else {
            d("ColoriViewModel:getAllColoris, size: " + allColoris.value?.size)
        }
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
        d("ColoriViewModel:getAllColoris, size: " + allColoris.value?.size)
        val tabSize = if (allColoris.value == null) 1 else allColoris.value!!.size
        d("ColoriViewModel:getAllColoris, size: $tabSize")
        val res = Array(size = tabSize) { i ->
            if (allColoris.value != null) {
                allColoris.value!![i]
            } else {
                Colori(0, "0")
            }
        }
        return res
    }

    fun getColoriById(id: Int) : Colori {
        getAllObjs().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Colori(0, "0")
    }

    fun getValueForId(id: Int) : String {
        return getColoriById(id).elem
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
