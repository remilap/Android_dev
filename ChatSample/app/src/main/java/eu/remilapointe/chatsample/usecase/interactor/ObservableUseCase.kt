package com.remilapointe.laser.usecase.interactor

import com.remilapointe.laser.usecase.Params
import io.reactivex.Observable

interface ObservableUseCase<T, P : Params> {
    fun getObservable(params: P? = null): Observable<T>
}