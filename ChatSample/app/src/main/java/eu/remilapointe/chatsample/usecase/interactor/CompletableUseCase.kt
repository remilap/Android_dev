package com.remilapointe.laser.usecase.interactor

import com.remilapointe.laser.usecase.Params
import io.reactivex.Completable

interface CompletableUseCase<P : Params> {
    fun getCompletable(params: P? = null): Completable
}
