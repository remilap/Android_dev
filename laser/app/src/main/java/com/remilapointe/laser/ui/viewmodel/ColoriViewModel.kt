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

    private val coloriRepo: ColoriRepo

    val allColoris: LiveData<MutableList<Colori>>

    init {
        d("ColoriViewModel:init")
        val coloriDao = LaserRoomDatabase.getDatabase(application).coloriDao()
        coloriRepo = ColoriRepo(coloriDao)
        allColoris = coloriRepo.allColoris
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
        d("View Model insert ${Colori.ELEM}: $colori")
        coloriRepo.insert(colori)
    }

    fun remove(colori: Colori) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get ${Colori.ELEM}: ${colori.elem}")
        coloriRepo.remove(colori)
    }

    fun update(colori: Colori) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model update ${Colori.ELEM} ${Colori.PRIM_KEY}: ${colori.id} with value ${colori.elem}")
        coloriRepo.update(colori)
    }

    fun getAllColoris() : Array<Colori> =
        Array(size = allColoris.value?.size!!) { i -> allColoris.value?.get(i)!! }

    fun getColoriById(id: Int) : Colori {
        getAllColoris().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Colori(0, "0")
    }

    fun getValueForId(id: Int) : String = getColoriById(id).elem

    fun getIdForValue(elem: String) : Int {
        getAllColoris().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

}
