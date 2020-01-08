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

    private val tailleRepo: TailleRepo

    val allTailles: LiveData<MutableList<Taille>>

    init {
        val tailleDao = LaserRoomDatabase.getDatabase(application).tailleDao()
        tailleRepo = TailleRepo(tailleDao)
        allTailles = tailleRepo.allTailles
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(taille: String) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert ${Taille.ELEM}: $taille")
        tailleRepo.insert(taille)
    }

    fun remove(taille: Taille) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get ${Taille.ELEM}: ${taille.elem}")
        tailleRepo.remove(taille)
    }

    fun getAllTailles() : Array<Taille> =
        Array(size = allTailles.value?.size!!) { i -> allTailles.value?.get(i)!! }

    fun getTailleById(id: Int) : Taille {
        getAllTailles().forEach {
            if (it.id == id) {
                return it
            }
        }
        return Taille(0, "0")
    }

    fun getValueForId(id: Int) : String = getTailleById(id).elem

    fun getIdForValue(elem: String) : Int {
        getAllTailles().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

}
