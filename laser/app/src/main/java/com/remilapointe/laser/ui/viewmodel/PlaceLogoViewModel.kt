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

    private val placeLogoRepo: PlaceLogoRepo

    val allPlaceLogos: LiveData<MutableList<PlaceLogo>>

    init {
        val placeLogoDao = LaserRoomDatabase.getDatabase(application).placeLogoDao()
        placeLogoRepo = PlaceLogoRepo(placeLogoDao)
        allPlaceLogos = placeLogoRepo.allPlaceLogos
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(placeLogo: String) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model insert ${PlaceLogo.ELEM}: $placeLogo")
        placeLogoRepo.insert(placeLogo)
    }

    fun remove(placeLogo: PlaceLogo) = viewModelScope.launch(Dispatchers.IO) {
        d("View Model get ${PlaceLogo.ELEM}: ${placeLogo.elem}")
        placeLogoRepo.remove(placeLogo)
    }

    fun getAllPlaceLogos() : Array<PlaceLogo> {
        return Array(size = allPlaceLogos.value?.size!!) { i -> allPlaceLogos.value?.get(i)!! }
    }

    fun getPlaceLogoById(id: Int) : PlaceLogo {
        getAllPlaceLogos().forEach {
            if (it.id == id) {
                return it
            }
        }
        return PlaceLogo(0, "0")
    }

    fun getValueForId(id: Int) : String = getPlaceLogoById(id).elem

    fun getIdForValue(elem: String) : Int {
        getAllPlaceLogos().forEach {
            if (it.elem == elem) {
                return it.id
            }
        }
        return -1
    }

}
