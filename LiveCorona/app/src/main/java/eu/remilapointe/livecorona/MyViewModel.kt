package eu.remilapointe.livecorona

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.remilapointe.livecorona.network.data.Country
import eu.remilapointe.livecorona.repos.RemoteRepository

class MyViewModel : ViewModel() {

    fun callAPI() : MutableLiveData<Country>
    {
        return RemoteRepository().callAPI()
    }

}
