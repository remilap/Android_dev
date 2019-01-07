package com.remilapointe.modernandroidapp

import com.remilapointe.modernandroidapp.data.RepoModel

class MainViewModel {
    var repoModel: RepoModel = RepoModel()
    var text: String = ""
    var isLoading: Boolean = false

    val onDataReadyCallback = object : RepoModel.OnDataReadyCallback {
        override fun onDataReady(data: String) {
            text = data
        }
    }

    fun refresh(){
        repoModel.refreshData(onDataReadyCallback)
    }

}