package com.remilapointe.laser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.log4k.d
import com.remilapointe.laser.db.LaserRoomDatabase
import com.remilapointe.laser.db.PlaceLogo
import com.remilapointe.laser.repo.PlaceLogoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceLogoViewModel(application: Application) : AndroidViewModel(application) {

    private val myRepo: PlaceLogoRepo

    val allObjs: LiveData<MutableList<PlaceLogo>>

    init {
        val myDao = LaserRoomDatabase.getDatabase(application, viewModelScope).placeLogoDao()
        myRepo = PlaceLogoRepo(myDao)
        allObjs = myRepo.allObjs
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(placeLogo: String) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert coloriId: $placeLogo")
        myRepo.insert(placeLogo)
    }

    fun remove(placeLogo: PlaceLogo) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get coloriId: ${placeLogo.elem}")
        myRepo.remove(placeLogo)
    }

    fun getAllObjs() : Array<PlaceLogo> {
        return Array(size = allObjs.value?.size!!) { i -> allObjs.value?.get(i)!! }
    }

    fun getPlaceLogoById(id: Int) : PlaceLogo {
        getAllObjs().forEach {
            if (it.id == id) {
                return it
            }
        }
        return PlaceLogo(0, "0")
    }

    fun getValueForId(id: Int) : String {
        return getPlaceLogoById(id).elem
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
