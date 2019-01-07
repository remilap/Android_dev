package com.remilapointe.modernandroidapp.uimodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.remilapointe.modernandroidapp.BR

class Repository(repositoryName: String?, var repositoryOwner: String?, var numberOfStars: Int?, var hasIssues: Boolean = false): BaseObservable() {
    @get:Bindable
    var repositoryName: String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.repositoryName)
    }

}
