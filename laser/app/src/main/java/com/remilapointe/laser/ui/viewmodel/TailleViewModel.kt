package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.LaserRoomDatabase
import com.remilapointe.laser.db.Taille
import com.remilapointe.laser.repo.TailleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TailleViewModel(application: Application) : AndroidViewModel(application) {

    private val myRepo: TailleRepo

    val allObjs: LiveData<MutableList<Taille>>

    init {
        val myDao = LaserRoomDatabase.getDatabase(application, viewModelScope).tailleDao()
        myRepo = TailleRepo(myDao)
        allObjs = myRepo.allObjs
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(taille: String) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert taille: $taille")
        myRepo.insert(taille)
    }

    fun remove(taille: Taille) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get taille: ${taille.elem}")
        myRepo.remove(taille)
    }

    fun getAllObjs() : Array<Taille> {
        return Array(size = allObjs.value?.size!!) { i -> allObjs.value?.get(i)!! }
    }

    fun getTailleById(id: Int) : Taille? {
        getAllObjs().forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    fun getValueForId(id: Int) : String? {
        return getTailleById(id)?.elem
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
